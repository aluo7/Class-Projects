import numpy as np
import math

import neuronxcc.nki as nki
import neuronxcc.nki.language as nl
import neuronxcc.nki.isa as nisa
from neuronxcc.nki import baremetal


"""
A convolution kernel that you need to implement.

Parameters:
    X: the input tensor
    W: the weights of the convolution filters.
    bias: the biases of the convolution filters.

expect: X.shape == [batch_size, in_channels, input_height, input_width]
expect: W.shape == [out_channels, in_channels, filter_height, filter_width]
expect: bias.shape == [out_channels]
expect: filter_height == filter_width
expect: input_channels % 128 == 0
expect: output_channels % 128 == 0

out_height = input_height - filter_height + 1
out_width = input_width - filter_width + 1

out_pool_height = out_height
out_pool_width = out_width

The shape of the output should be [batch_size, out_channels, out_pool_height, out_pool_width]

"""

@nki.jit
def conv2d(X, W, bias):

    batch_size, in_channels, input_height, input_width = X.shape
    out_channels, in_channels_, filter_height, filter_width = W.shape
    out_channels_ = bias.shape[0]

    assert (
        in_channels_ == in_channels and out_channels_ == out_channels
    ), f"Shape mismatch. {in_channels}, {in_channels_}, {out_channels}, {out_channels_}"

    out_height = input_height - filter_height + 1
    out_width = input_width - filter_width + 1

    out_pool_height = out_height
    out_pool_width = out_width
    
    # Can assume multiple of 128 to avoid using mask
    assert in_channels % 128 == 0

    # Can assume one PSUM bank can at least fit one row of the pixels
    assert nl.tile_size.gemm_moving_fmax >= out_width

    # Initialize output array
    X_out = nl.ndarray(
        shape=(batch_size, out_channels, out_pool_height, out_pool_width),
        dtype=X.dtype,
        buffer=nl.hbm,
    )

    # Various tiling dimensions (You may want to define more of them)
    d_c_out = nl.tile_size.pmax
    n_tiles_c_out = out_channels // d_c_out
    d_c_in = nl.tile_size.pmax
    n_tiles_c_in = in_channels // d_c_in
    
    # Height tiling
    d_ho = nl.tile_size.gemm_moving_fmax // out_width
    d_ho = max(min(d_ho, out_height), 1)
    n_tiles_ho = (out_height + d_ho - 1) // d_ho
    
    # Pre-transpose weights
    W_reshaped = W.reshape((n_tiles_c_out, d_c_out, n_tiles_c_in, d_c_in, filter_height, filter_width))
    weight_sbuf = nl.ndarray((n_tiles_c_out, nl.par_dim(d_c_out), n_tiles_c_in, d_c_in, filter_height, filter_width), dtype=W.dtype, buffer=nl.sbuf)
    
    for k in nl.affine_range(n_tiles_c_out):
        weight_sbuf[k] = nl.load(W_reshaped[k])
    
    weight_copy = nl.ndarray((filter_height, filter_width, n_tiles_c_out, n_tiles_c_in, nl.par_dim(d_c_out), d_c_in), dtype=W.dtype, buffer=nl.sbuf)
    w_transposed = nl.ndarray((filter_height, filter_width, n_tiles_c_out, n_tiles_c_in, nl.par_dim(d_c_in), d_c_out), dtype=W.dtype, buffer=nl.sbuf)
    
    for k in nl.affine_range(n_tiles_c_out):
        for c in nl.affine_range(n_tiles_c_in):
            for r in nl.affine_range(filter_height):
                for s in nl.affine_range(filter_width):
                    weight_copy[r, s, k, c, :, :] = nl.copy(weight_sbuf[k, :, c, :, r, s])
                    w_transposed[r, s, k, c] = nisa.nc_transpose(weight_copy[r, s, k, c])
    
    for b in nl.affine_range(batch_size):
        for tile_ho in nl.affine_range(n_tiles_ho):
            ho_start = tile_ho * d_ho
            cur_ho = min(d_ho, out_height - ho_start)
            
            for tile_c_out in nl.affine_range(n_tiles_c_out):
                c_out_start = tile_c_out * d_c_out
                
                output_tile_psum = nl.ndarray((d_c_out, d_ho * out_width), dtype=nl.float32, buffer=nl.psum)
                output_tile_psum[:] = 0
                
                hi_start = ho_start
                hi_end = ho_start + d_ho + filter_height - 1
                hi_load_end = min(hi_end, input_height)
                load_height = hi_load_end - hi_start
                
                for tile_c_in in nl.affine_range(n_tiles_c_in):
                    c_in_start = tile_c_in * d_c_in
                    
                    input_patch_sbuf = nl.ndarray((nl.par_dim(d_c_in), d_ho + filter_height - 1, input_width), dtype=X.dtype, buffer=nl.sbuf)
                    input_patch_sbuf[:] = 0.0
                    
                    input_loaded = nl.load(X[b, c_in_start:c_in_start + d_c_in, hi_start:hi_load_end, :])
                    input_patch_sbuf[:, 0:load_height, :] = nl.copy(input_loaded)
                    
                    for fh in nl.affine_range(filter_height):
                        for fw in nl.affine_range(filter_width):
                            input_tile_3d = input_patch_sbuf[:, fh:fh + d_ho, fw:fw + out_width]
                            
                            # Manual reshape: [d_c_in, d_ho, out_width] -> [d_c_in, d_ho * out_width]
                            input_tile_2d = nl.ndarray((d_c_in, d_ho * out_width), dtype=X.dtype, buffer=nl.sbuf)
                            for h_idx in nl.affine_range(d_ho):
                                input_tile_2d[:, h_idx*out_width:(h_idx+1)*out_width] = input_tile_3d[:, h_idx, :]
                            
                            weight_tile_2d = w_transposed[fh, fw, tile_c_out, tile_c_in]
                            
                            output_tile_psum += nl.matmul(weight_tile_2d, input_tile_2d, transpose_x=True)
                
                bias_tile = nl.load(bias[c_out_start : c_out_start + d_c_out])
                
                valid_length = cur_ho * out_width
                valid_psum = output_tile_psum[:, 0:valid_length]
                valid_psum = valid_psum + bias_tile.reshape((d_c_out, 1))
                
                output_tile_sbuf = nl.copy(valid_psum)
                
                # Manual reshape: [d_c_out, cur_ho * out_width] -> [d_c_out, cur_ho, out_width]
                output_tile_3d = nl.ndarray((d_c_out, cur_ho, out_width), dtype=X.dtype, buffer=nl.sbuf)
                for h_idx in nl.affine_range(cur_ho):
                    output_tile_3d[:, h_idx, :] = output_tile_sbuf[:, h_idx*out_width:(h_idx+1)*out_width]
                
                nl.store(X_out[b, c_out_start:c_out_start + d_c_out, ho_start:ho_start + cur_ho, :], value=output_tile_3d)
    
    return X_out