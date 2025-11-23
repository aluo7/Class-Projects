import matplotlib.pyplot as plt
import numpy as np

optimizations = [
    'o0 (Naive)',
    'o1 (Seq. Access)',
    'o2 (Tiling)',
    'o3 (OpenMP)',
    'o4 (-O3)'
]

# Timings in ms for N=100
times_100 = {
    'o0 (Naive)': 3.67689,
    'o1 (Seq. Access)': 3.29455,
    'o2 (Tiling)': 3.92273,
    'o3 (OpenMP)': 1.38116,
    'o4 (-O3)': 0.981671
}

# Timings in ms for N=1000
times_1000 = {
    'o0 (Naive)': 3369.72,
    'o1 (Seq. Access)': 2755.53,
    'o2 (Tiling)': 3240.78,
    'o3 (OpenMP)': 588.09,
    'o4 (-O3)': 555.416
}

baseline_100 = times_100['o0 (Naive)']
speedups_100 = [baseline_100 / times_100[opt] for opt in optimizations]

baseline_1000 = times_1000['o0 (Naive)']
speedups_1000 = [baseline_1000 / times_1000[opt] for opt in optimizations]

x = np.arange(len(optimizations))
width = 0.35

fig, ax = plt.subplots(figsize=(12, 7))

rects1 = ax.bar(x - width/2, speedups_100, width, label='Size = 100x100x100')
rects2 = ax.bar(x + width/2, speedups_1000, width, label='Size = 1000x1000x1000')

ax.set_ylabel('Speedup (relative to Naive o0)')
ax.set_title('CPU Matrix Multiplication Speedup', fontsize=16)
ax.set_xticks(x)
ax.set_xticklabels(optimizations, rotation=10, ha="right")
ax.legend()

ax.axhline(y=1, color='grey', linestyle='--', linewidth=1)

ax.bar_label(rects1, padding=3, fmt='%.2fx')
ax.bar_label(rects2, padding=3, fmt='%.2fx')

fig.tight_layout()

plt.savefig('./cpu_speedup.png')

print("Plot saved to cpu_speedup.png")