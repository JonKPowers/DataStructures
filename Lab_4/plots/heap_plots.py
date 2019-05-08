import pandas
import matplotlib.pyplot as plt

conversion_factor = 1000000

##############################
## HeapSort Chart
##############################

heap_sorted_data = pandas.read_csv('../output/HSort_n10_sorted.csv', index_col=0)
heap_reversed_data = pandas.read_csv('../output/HSort_n10_reversed.csv', index_col=0)
heap_random_data = pandas.read_csv('../output/HSort_n10_random.csv', index_col=0)
heap_duplicates_data = pandas.read_csv('../output/HSort_n10_duplicates.csv', index_col=0)


heap_sorted_x = heap_sorted_data.index
heap_sorted_y = list(heap_sorted_data.iloc[:,0] / conversion_factor)
heap_reversed_x = heap_reversed_data.index
heap_reversed_y = list(heap_reversed_data.iloc[:,0] / conversion_factor)
heap_random_x = heap_random_data.index
heap_random_y = list(heap_random_data.iloc[:,0] / conversion_factor)
heap_duplicates_x = heap_duplicates_data.index
heap_duplicates_y = list(heap_duplicates_data.iloc[:,0] / conversion_factor)


fig, ax = plt.subplots(2, 1, sharex='col', dpi=250, constrained_layout=True)
ax[0].set_title('Heap Sort Performance')
ax[1].set_xlabel('input size')
ax[1].set_ylabel('time (ms)')

ax[0].scatter(heap_sorted_x, heap_sorted_y, s=0.8, label='Sorted')
ax[0].scatter(heap_reversed_x, heap_reversed_y, s=0.8, label='Reversed')
ax[0].scatter(heap_random_x, heap_random_y, s=0.8, label='Random')
ax[1].scatter(heap_duplicates_x, heap_duplicates_x, s=0.8, label='Duplicates', c='r')

ax[0].legend()
ax[1].legend()
plt.savefig('HeapSortPerformance.png', dpi='figure')

