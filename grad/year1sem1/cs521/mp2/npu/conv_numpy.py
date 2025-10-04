import numpy as np
import torch


def conv2d_cpu_torch(X, W, bias, pad_size=0):
    X = torch.tensor(X)
    W = torch.tensor(W)
    bias = torch.tensor(bias)

    conv_out = torch.nn.functional.conv2d(X, W, bias, stride=1, padding=pad_size)

    return conv_out

"""
A NumPy implementation of the forward pass for a convolutional layer.
"""
def conv_numpy(X, W, bias):
    out = None
    
    batch_size, in_channels, input_height, input_width = X.shape
    out_channels, _, filter_height, filter_width = W.shape

    H_out = 1 + (input_height - filter_height)
    W_out = 1 + (input_width - filter_width)

    out = np.zeros((batch_size, out_channels, H_out, W_out))
    for b in range(batch_size):
        for c in range(out_channels):
            for i in range(H_out):
                for j in range(W_out):
                    x_ij = X[b, :, i : i + filter_height, j : j + filter_width]
                    out[b, c, i, j] = np.sum(x_ij * W[c]) + bias[c]

    return out
