import matplotlib.pyplot as plt
import numpy as np

# trials
configurations = [
    'Small\n(N=8, C=32, H=128)',
    'Medium\n(N=16, C=64, H=128)',
    'Large\n(N=16, C=128, H=256)'
]

# gpu kernel times
eager_times = [62.62, 73.46, 984.26]
inductor_times = [1.03, 2.23, 39.79]
jax_times = [1.53, 6.21, 67.78]

x = np.arange(len(configurations))
width = 0.25
multiplier = 0

fig, ax = plt.subplots(layout='constrained', figsize=(10, 6))

performance_data = {
    'Eager': eager_times,
    'Inductor': inductor_times,
    'JAX': jax_times,
}

for framework, times in performance_data.items():
    offset = width * multiplier
    rects = ax.bar(x + offset, times, width, label=framework)
    ax.bar_label(rects, padding=3, fmt='%.2f')
    multiplier += 1

ax.set_ylabel('GPU Kernel Time (ms) - Logarithmic Scale')
ax.set_title('GPU Performance Comparison of Convolution Implementations')
ax.set_xticks(x + width, configurations)
ax.legend(loc='upper left', ncols=3)

ax.set_yscale('log')

ax.set_ylim(0.1, 2000)

plt.savefig('gpu_performance_comparison.png', dpi=300)

print("Plot has been saved as 'gpu_performance_comparison.png'")

plt.show()
