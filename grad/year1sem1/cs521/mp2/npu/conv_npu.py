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
    c_in_pmax = nl.tile_size.pmax
    n_tiles_c_in = in_channels // c_in_pmax

    out_tile_h = int(min(nl.tile_size.gemm_moving_fmax // out_width, out_height))  # output tile height
    out_tile_h = 1 if out_tile_h <= 0 else out_tile_h
    out_tile_w = out_width  # output tile width
    num_out_tiles_h = (out_height + out_tile_h - 1) // out_tile_h  # num tiles along output height
    out_tile_ch = nl.tile_size.pmax  # output tile channels
    num_out_tiles_ch = out_channels // out_tile_ch  # num tiles along output channels

    # buffer for final reordered weights
    w_sbuf = nl.ndarray(
        (filter_height, filter_width, num_out_tiles_ch, n_tiles_c_in, nl.par_dim(c_in_pmax), out_tile_ch),
        dtype=W.dtype,
        buffer=nl.sbuf
    )

    # temporary buffer for loading weight tiles
    w_sbuf_tmp = nl.ndarray(
        (nl.par_dim(out_tile_ch), n_tiles_c_in, c_in_pmax, filter_height, filter_width),
        dtype=W.dtype,
        buffer=nl.sbuf
    )

    # reshape for tiling
    W = W.reshape((num_out_tiles_ch, out_tile_ch, n_tiles_c_in, c_in_pmax, filter_height, filter_width))

    # load transposed weights into buffer
    for tile_k in nl.sequential_range(num_out_tiles_ch):
        w_sbuf_tmp[...] = nl.load(W[tile_k])

        for tile_c in nl.affine_range(n_tiles_c_in):
            for fh in nl.affine_range(filter_height):
                for fw in nl.affine_range(filter_width):
                    w_sbuf[fh, fw, tile_k, tile_c] = nisa.nc_transpose(
                        w_sbuf_tmp[:, tile_c, :, fh, fw]
                    )

    # main conv loop
    for batch_idx in nl.affine_range(batch_size):
        for tile_oh_idx in nl.affine_range(num_out_tiles_h):
            for tile_k_idx in nl.affine_range(num_out_tiles_ch):

                acc_tile = nl.zeros(
                    (out_tile_ch, out_tile_h * out_tile_w),
                    dtype=nl.float32,
                    buffer=nl.psum
                )

                bias_tile = nl.load(bias[tile_k_idx * out_tile_ch:(tile_k_idx + 1) * out_tile_ch])

                for tile_c_idx in nl.affine_range(n_tiles_c_in):
                    ih_start = tile_oh_idx * out_tile_h
                    ih_end = (tile_oh_idx + 1) * out_tile_h + 2 * (filter_height // 2)

                    x_tile = nl.load(
                        X[
                            batch_idx,
                            tile_c_idx * c_in_pmax:(tile_c_idx + 1) * c_in_pmax,
                            ih_start:ih_end,
                            :
                        ]
                    )

                    for fh in nl.affine_range(filter_height):
                        for fw in nl.affine_range(filter_width):
                            x_window = nl.copy(
                                x_tile[:, fh:fh + out_tile_h, fw:fw + out_tile_w]
                            ).reshape((c_in_pmax, out_tile_h * out_tile_w))

                            acc_tile += nl.matmul(
                                w_sbuf[fh, fw, tile_k_idx, tile_c_idx],
                                x_window,
                                transpose_x=True
                            )

                # add bias and write output
                out_tile = acc_tile + bias_tile
                out_tile = out_tile.reshape((out_tile_ch, out_tile_h, out_tile_w))

                nl.store(
                    X_out[
                        batch_idx,
                        tile_k_idx * out_tile_ch:(tile_k_idx + 1) * out_tile_ch,
                        tile_oh_idx * out_tile_h:(tile_oh_idx + 1) * out_tile_h,
                        :
                    ],
                    out_tile
                )

    return X_out
