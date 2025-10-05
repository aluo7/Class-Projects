import torch
import torch.nn as nn
import torch.nn.functional as F
from torch.profiler import profile, record_function, ProfilerActivity
from myconv import ConvModel

if __name__ == "__main__":
    torch.manual_seed(0)

    # Instantiate your PyTorch model

    # trial 1: 1.031939 ms
    N, C, H, W = 8, 32, 128, 128
    out_channels = 64
    kernel_size = 3
    stride = 1
    padding = 1

    # trial 2: 2.231591 ms
    # N, C, H, W = 16, 64, 128, 128
    # out_channels = 128
    # kernel_size = 3
    # stride = 1
    # padding = 1

    # trial 1: 39.793284 ms
    # N, C, H, W = 16, 128, 256, 256
    # out_channels = 128
    # kernel_size = 5
    # stride = 1
    # padding = 2

    x = torch.randn(N, C, H, W, dtype=torch.float64).cuda()
    
    model = ConvModel(H, W, in_channels=C, out_channels=out_channels, kernel_size=kernel_size, stride=stride, padding=padding).to('cuda', dtype=torch.float64).eval()

    # Torch-Inductor compilation
    scripted_model = torch.compile(model, backend="inductor")

    with profile(
        activities=[ProfilerActivity.CPU, ProfilerActivity.CUDA],
        record_shapes=True,
        profile_memory=True
    ) as prof:
        # warmup run
        # with record_function("Inductor_First_Run_with_JIT"):
            # _ = scripted_model(x)
        
        with record_function("inductor_conv"):
            out = scripted_model(x)

    prof.export_chrome_trace("./trace/trace_inductor.json")
    
    # Test your solution
    conv_ref = F.conv2d(x, model.weight, model.bias, stride=stride, padding=padding)
    print("Inductor --- shape check:", out.shape == conv_ref.shape)
    print("Inductor --- correctness check:", torch.allclose(out, conv_ref, atol=1e-4))