import matplotlib.pyplot as plt
import pandas as pd
import io

csv_data = """N,Float_us,Dual_us,Overhead
10000,560,1951,3.48393
100000,5390,19095,3.54267
1000000,56526,196164,3.47033
10000000,577513,2010062,3.48055
50000000,2976437,10313262,3.46497
"""

df = pd.read_csv(io.StringIO(csv_data))

plt.figure(figsize=(10, 6))

# Execution Time vs N (Log Scale)
plt.subplot(1, 2, 1)
plt.plot(df['N'], df['Float_us'] / 1000, 'o-', label='Plain Float')
plt.plot(df['N'], df['Dual_us'] / 1000, 's-', label='Dual Number')
plt.xscale('log')
plt.yscale('log')
plt.xlabel('Iterations (N)')
plt.ylabel('Time (milliseconds)')
plt.title('Runtime Scaling')
plt.legend()
plt.grid(True, which="both", ls="-", alpha=0.5)

# Overhead
plt.subplot(1, 2, 2)
plt.plot(df['N'], df['Overhead'], 'r^-')
plt.xscale('log')
plt.ylim(0, 5)
plt.xlabel('Iterations (N)')
plt.ylabel('Overhead Factor (Dual / Float)')
plt.title('Computational Overhead')
plt.grid(True)

plt.tight_layout()
plt.savefig('overhead_plot.png')
plt.show()
