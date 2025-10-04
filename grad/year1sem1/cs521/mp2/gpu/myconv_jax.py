import jax
import jax.numpy as jnp
from jax import jit
import torch.nn.functional as F
import numpy as np
import torch
from myconv import ConvModel
from torch.profiler import profile, record_function, ProfilerActivity

# Create a log directory
logdir = "./jax_trace"

def im2col_manual_jax(x, KH, KW, S, P, out_h, out_w):
    ''' 
        Reimplement the same function (im2col_manual) in myconv.py "for JAX". 
        Hint: Instead of torch tensors, use of jnp arrays is required to leverage JIT compilation and GPU execution in JAX
    '''
    # x: (N, C, H, W)
    N, C, H, W = x.shape

    # Pad input
    x_pad = jnp.pad(x, ((0,0),(0,0),(P,P),(P,P)))

    # TO DO: Convert input (x) into shape (N, out_h*out_w, C*KH*KW). 
    # Refer to Lecture 3 for implementing this operation.

    y_starts = jnp.arange(out_h) * S
    x_starts = jnp.arange(out_w) * S

    k_y_offsets = jnp.arange(KH)
    k_x_offsets = jnp.arange(KW)

    y_indices = y_starts[:, None, None, None] + k_y_offsets[None, None, :, None]
    x_indices = x_starts[None, :, None, None] + k_x_offsets[None, None, None, :]
    
    patches = x_pad[:, :, y_indices, x_indices]

    patches = patches.transpose(0, 1, 4, 5, 2, 3)

    return patches.reshape(N, C * KH * KW, out_h * out_w)

def conv2d_manual_jax(x, weight, bias, stride=1, padding=1):
    '''
        Reimplement the same function (conv2d_manual) in myconv.py "for JAX". 
        Hint: Instead of torch tensors, use of jnp arrays is required to leverage JIT compilation and GPU execution in JAX
        Hint: Unlike PyTorch, JAX arrays are immutable, so you cannot do indexing like out[i:j, :] = ... inside a JIT. You may use .at[].set() instead.
    '''
    N, C, H, W = x.shape
    C_out, _, KH, KW = weight.shape

    # define your helper variables here
    out_h = int(((H - KH + 2 * padding) / stride) + 1)
    out_w = int(((W - KW + 2 * padding) / stride) + 1)
    
    # TO DO: 1) convert input (x) into shape (N, out_h*out_w, C*KH*KW).
    cols = im2col_manual_jax(x, KH, KW, stride, padding, out_h, out_w)

    # TO DO: 2) flatten self.weight into shape (C_out, C*KH*KW).
    weight_flattened = weight.reshape((C_out, C * KH * KW))

    # TO DO: 3) perform tiled matmul after required reshaping is done.
    out = jnp.matmul(weight_flattened, cols)

    # TO DO: 4) Add bias.
    out += jnp.reshape(bias, (1, C_out, 1))

    # TO DO: 5) reshape output into shape (N, C_out, out_h, out_w).
    out = out.reshape((N, C_out, out_h, out_w))

    return out

if __name__ == "__main__":
    # Instantiate PyTorch model
    H, W = 33, 33
    model = ConvModel(H, W, in_channels=3, out_channels=8, kernel_size=5, stride=1, padding=1)
    model.eval()

    # Example input
    x_torch = torch.randn(1, 3, H, W)

    # Export weights and biases
    params = {
        "weight": model.weight.detach().cpu().numpy(),  # shape (out_channels, in_channels, KH, KW)
        "bias": model.bias.detach().cpu().numpy()       # shape (out_channels,)
    }

    # Convert model input, weights and bias into jax arrays
    x_jax = jnp.array(x_torch.numpy())
    weight_jax = jnp.array(params["weight"])
    bias_jax = jnp.array(params["bias"])

    # enable JIT compilation
    conv2d_manual_jax_jit = jit(conv2d_manual_jax)

    jax.profiler.start_trace("./trace/jax_trace")

    # call your JAX function
    _ = conv2d_manual_jax_jit(x_jax, weight_jax, bias_jax)

    with profile(
        activities=[ProfilerActivity.CPU, ProfilerActivity.CUDA],
        record_shapes=True,
        profile_memory=True
    ) as prof:
        with record_function("manual_conv_jax"):
            out_jax = torch.from_numpy(np.array(conv2d_manual_jax_jit(x_jax, weight_jax, bias_jax)))

    prof.export_chrome_trace("./trace/trace_jax.json")

    # Test your solution
    conv_ref = F.conv2d(x_torch, model.weight, model.bias, stride=1, padding=1)
    print("JAX --- shape check:", out_jax.shape == conv_ref.shape)
    print("JAX --- correctness check:", torch.allclose(out_jax, conv_ref, atol=1e-1))
