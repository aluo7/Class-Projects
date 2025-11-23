import matplotlib.pyplot as plt
import numpy as np

scaling_sizes = [100, 500, 1000, 5000, 10000]
scaling_times_o4 = [3.67689, 89.2785, 3369.72, 82435.3, 668348]

fig2, ax2 = plt.subplots(figsize=(10, 6))

ax2.plot(scaling_sizes, scaling_times_o4, marker='o', linestyle='--')

# ax2.set_xscale('log')
# ax2.set_yscale('log')

ax2.set_xlabel('Matrix Size')
ax2.set_ylabel('Execution Time in ms')
ax2.set_title('Performance Scaling of Final Optimized Kernel (o4)', fontsize=16)
ax2.grid(True, which="both", ls="--")

fig2.tight_layout()
plt.savefig('./cpu_scaling.png')
print("Scaling study plot saved to cpu_scaling.png")