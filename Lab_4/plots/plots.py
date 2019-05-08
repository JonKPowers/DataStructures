import matplotlib
import pandas
import matplotlib.pyplot as plt
from matplotlib import colors as mcolors
import os
import re
import pathlib

output_dir = '../output/50to20000'

files = [pathlib.Path(output_dir, file) for file in os.listdir(output_dir)]
files = [file for file in files if file.is_file() and not file.name.startswith('.')]


##############################
##  Effect of pivot selection
##############################

k_value = 'k=0'
conversion_factor = 1000000

# Compare performance of pivot selections on k=1.
# Four plots: Sorted, reverse, random, duplicates (otherwise random)

# Sorted files
sorted_files = [file for file in files if '_sorted' in file.name]
sorted_first = [file for file in sorted_files if '_first_' in file.name][0]
sorted_last = [file for file in sorted_files if '_last_' in file.name][0]
sorted_mid = [file for file in sorted_files if '_mid_' in file.name][0]
sorted_median = [file for file in sorted_files if '_median_' in file.name][0]
sorted_random = [file for file in sorted_files if '_random_' in file.name][0]

# Reversed files
reversed_files = [file for file in files if '_reversed' in file.name]
reversed_first = [file for file in reversed_files if '_first_' in file.name][0]
reversed_last = [file for file in reversed_files if '_last_' in file.name][0]
reversed_mid = [file for file in reversed_files if '_mid_' in file.name][0]
reversed_median = [file for file in reversed_files if '_median_' in file.name][0]
reversed_random = [file for file in reversed_files if '_random_' in file.name][0]

# Random files
random_files = [file for file in files if '_random' in file.name]
random_first = [file for file in random_files if '_first_' in file.name][0]
random_last = [file for file in random_files if '_last_' in file.name][0]
random_mid = [file for file in random_files if '_mid_' in file.name][0]
random_median = [file for file in random_files if '_median_' in file.name][0]
random_random = [file for file in random_files if '_random_' in file.name][0]

# Duplicates files
duplicates_files = [file for file in files if '_duplicates' in file.name]
duplicates_first = [file for file in duplicates_files if '_first_' in file.name][0]
duplicates_last = [file for file in duplicates_files if '_last_' in file.name][0]
duplicates_mid = [file for file in duplicates_files if '_mid_' in file.name][0]
duplicates_median = [file for file in duplicates_files if '_median_' in file.name][0]
duplicates_random = [file for file in duplicates_files if '_random_' in file.name][0]

# Sorted
sorted_first_data = pandas.read_csv(sorted_first, index_col=0)
sorted_first_x = list(sorted_first_data.index)
sorted_first_y = list(sorted_first_data[k_value] / conversion_factor)

sorted_last_data = pandas.read_csv(sorted_last, index_col=0)
sorted_last_x = list(sorted_last_data.index)
sorted_last_y = list(sorted_last_data[k_value] / conversion_factor)

sorted_mid_data = pandas.read_csv(sorted_mid, index_col=0)
sorted_mid_x = list(sorted_mid_data.index)
sorted_mid_y = list(sorted_mid_data[k_value] / conversion_factor)

sorted_median_data = pandas.read_csv(sorted_median, index_col=0)
sorted_median_x = list(sorted_median_data.index)
sorted_median_y = list(sorted_median_data[k_value] / conversion_factor)

sorted_random_data = pandas.read_csv(sorted_random, index_col=0)
sorted_random_x = list(sorted_random_data.index)
sorted_random_y = list(sorted_random_data[k_value] / conversion_factor)

# Reversed
reversed_first_data = pandas.read_csv(reversed_first, index_col=0)
reversed_first_x = list(reversed_first_data.index)
reversed_first_y = list(reversed_first_data[k_value] / conversion_factor)

reversed_last_data = pandas.read_csv(reversed_last, index_col=0)
reversed_last_x = list(reversed_last_data.index)
reversed_last_y = list(reversed_last_data[k_value] / conversion_factor)

reversed_mid_data = pandas.read_csv(reversed_mid, index_col=0)
reversed_mid_x = list(reversed_mid_data.index)
reversed_mid_y = list(reversed_mid_data[k_value] / conversion_factor)

reversed_median_data = pandas.read_csv(reversed_median, index_col=0)
reversed_median_x = list(reversed_median_data.index)
reversed_median_y = list(reversed_median_data[k_value] / conversion_factor)

reversed_random_data = pandas.read_csv(reversed_random, index_col=0)
reversed_random_x = list(reversed_random_data.index)
reversed_random_y = list(reversed_random_data[k_value] / conversion_factor)

# Random
random_first_data = pandas.read_csv(random_first, index_col=0)
random_first_x = list(random_first_data.index)
random_first_y = list(random_first_data[k_value] / conversion_factor)

random_last_data = pandas.read_csv(random_last, index_col=0)
random_last_x = list(random_last_data.index)
random_last_y = list(random_last_data[k_value] / conversion_factor)

random_mid_data = pandas.read_csv(random_mid, index_col=0)
random_mid_x = list(random_mid_data.index)
random_mid_y = list(random_mid_data[k_value] / conversion_factor)

random_median_data = pandas.read_csv(random_median, index_col=0)
random_median_x = list(random_median_data.index)
random_median_y = list(random_median_data[k_value] / conversion_factor)

random_random_data = pandas.read_csv(random_random, index_col=0)
random_random_x = list(random_random_data.index)
random_random_y = list(random_random_data[k_value] / conversion_factor)

# Duplicates
duplicates_first_data = pandas.read_csv(duplicates_first, index_col=0)
duplicates_first_x = list(duplicates_first_data.index)
duplicates_first_y = list(duplicates_first_data[k_value] / conversion_factor)

duplicates_last_data = pandas.read_csv(duplicates_last, index_col=0)
duplicates_last_x = list(duplicates_last_data.index)
duplicates_last_y = list(duplicates_last_data[k_value] / conversion_factor)

duplicates_mid_data = pandas.read_csv(duplicates_mid, index_col=0)
duplicates_mid_x = list(duplicates_mid_data.index)
duplicates_mid_y = list(duplicates_mid_data[k_value] / conversion_factor)

duplicates_median_data = pandas.read_csv(duplicates_median, index_col=0)
duplicates_median_x = list(duplicates_median_data.index)
duplicates_median_y = list(duplicates_median_data[k_value] / conversion_factor)

duplicates_random_data = pandas.read_csv(duplicates_random, index_col=0)
duplicates_random_x = list(duplicates_random_data.index)
duplicates_random_y = list(duplicates_random_data[k_value] / conversion_factor)

# Set up axes for plots
fig, ax = plt.subplots(1, 2, constrained_layout=True, dpi=250)
ax_sorted = ax[0]
ax_reversed = ax[1]

# Sorted plot
ax_sorted.scatter(sorted_first_x, sorted_first_y, s=1, label='First')
ax_sorted.scatter(sorted_last_x, sorted_last_y, s=1, label='Last')
ax_sorted.scatter(sorted_mid_x, sorted_mid_y, s=1, label='Middle')
ax_sorted.scatter(sorted_random_x, sorted_random_y, s=1, label='Random')
ax_sorted.scatter(sorted_median_x, sorted_median_y, s=1, label='Median')
ax_sorted.legend()

# Reversed plot
ax_reversed.scatter(reversed_first_x, reversed_first_y, s=1, label='First')
ax_reversed.scatter(reversed_last_x, reversed_last_y, s=1, label='Last')
ax_reversed.scatter(reversed_mid_x, reversed_mid_y, s=1, label='Middle')
ax_reversed.scatter(reversed_random_x, reversed_random_y, s=1, label='Random item pivot')
ax_reversed.scatter(reversed_median_x, reversed_median_y, s=1, label='Median of 3 pivot')

ax_sorted.set_title('Sorted input')
ax_reversed.set_title('Reversed input')
plt.savefig('Pivot on performance SortedReversed.png', dpi='figure')






fig, ax = plt.subplots(1, 2, constrained_layout=True, dpi=250)
ax_random = ax[0]
ax_duplicates = ax[1]
# Random plot

ax_random.scatter(random_first_x, random_first_y, s=1, label='First item')
ax_random.scatter(random_last_x, random_last_y, s=1, label='Last item')
ax_random.scatter(random_mid_x, random_mid_y, s=1, label='Middle item')
ax_random.scatter(random_random_x, random_random_y, s=1, label='Random')
ax_random.scatter(random_median_x, random_median_y, s=1, label='Median of 3')
ax_random.set_ylabel('time (ms)')
ax_random.set_xlabel('input size')
ax_random.set_ylim([0, 2.0])

# Duplicates plot
ax_duplicates.scatter(duplicates_first_x, duplicates_first_y, s=1, label='First item pivot')
ax_duplicates.scatter(duplicates_last_x, duplicates_last_y, s=1, label='Last item pivot')
ax_duplicates.scatter(duplicates_mid_x, duplicates_mid_y, s=1, label='Middle item pivot')
ax_duplicates.scatter(duplicates_random_x, duplicates_random_y, s=1, label='Random item pivot')
ax_duplicates.scatter(duplicates_median_x, duplicates_median_y, s=1, label='Median of 3 pivot')
ax_duplicates.set_ylim([0, 2.0])

# Title
# fig.suptitle(f'Effect of pivot selection on Quicksort ({k_value})')
ax_sorted.set_title('Sorted input')
ax_reversed.set_title('Reversed input')
ax_random.set_title('Random input')
ax_duplicates.set_title('Duplicates input (20%)')
ax_random.legend(title='Pivot')
# plt.show()
plt.savefig('Pivot on performance RandDupes.png', dpi='figure')
plt.clf()


##############################
# Isolate Random and middle pivots
##############################

fig, ax = plt.subplots(1, 2, constrained_layout=True, sharey='row', figsize=(9.6, 5.2), dpi=250)
ax_sorted = ax[0]
ax_reversed = ax[1]

# Sorted plot
ax_sorted.scatter(sorted_mid_x, sorted_mid_y, s=2, label='Middle')
ax_sorted.scatter(sorted_random_x, sorted_random_y, s=2, label='Random')
ax_sorted.set_ylim([0, 1.2])

ax_sorted.set_xlabel('input size')
ax_sorted.set_ylabel('time(ms)')
ax_sorted.legend(loc='upper left')

# Reversed plot
ax_reversed.scatter(reversed_mid_x, reversed_mid_y, s=2, label='Middle')
ax_reversed.scatter(reversed_random_x, reversed_random_y, s=2, label='Random item pivot')
ax_reversed.set_ylim([0, 1.2])

# Title
# fig.suptitle(f'Effect of pivot selection on Quicksort ({k_value})')
ax_sorted.set_title('Sorted input')
ax_reversed.set_title('Reversed input')

# plt.show()
plt.savefig('Pivot on performance-MidAndRandZoom.png', dpi='figure')
plt.clf()


##############################
# Effect of K values
##############################

sizes = [50, 650, 1050, 2050, 5050, 10050, 15050, 21850]

# Effect of input file type
n = 9050
colors = dict(mcolors.BASE_COLORS, **mcolors.CSS4_COLORS)
sorted_input_color = colors['red']
reversed_input_color = colors['blue']
random_input_color = colors['limegreen']
duplicates_input_color = colors['orange']

for n in sizes:
    ################
    # First Item pivot
    sorted_first_ks = [re.search(r'\d+', item).group(0) for item in sorted_first_data.columns]
    sorted_first_y = sorted_first_data.loc[n] / conversion_factor

    reversed_first_ks = [re.search(r'\d+', item).group(0) for item in reversed_first_data.columns]
    reversed_first_y = reversed_first_data.loc[n] / conversion_factor

    random_first_ks = [re.search(r'\d+', item).group(0) for item in random_first_data.columns]
    random_first_y = random_first_data.loc[n] / conversion_factor

    duplicates_first_ks = [re.search(r'\d+', item).group(0) for item in duplicates_first_data.columns]
    duplicates_first_y = duplicates_first_data.loc[n] / conversion_factor

    # Set up plots
    fig, ax = plt.subplots(2, 1, sharex='col', constrained_layout=True, dpi=250)
    ax_top = ax[0]
    ax_bottom = ax[1]

    ax_top.scatter(sorted_first_ks, sorted_first_y, c=sorted_input_color, label='Sorted')
    ax_top.scatter(reversed_first_ks, reversed_first_y, c=reversed_input_color, label='Reversed')
    ax_bottom.scatter(random_first_ks, random_first_y, c=random_input_color, label='Random')
    ax_bottom.scatter(duplicates_first_ks, duplicates_first_y, c=duplicates_input_color, label='Duplicates')

    ax_top.set_title(f'Using Insertion Sort to Finish (first item pivot, n={n})')
    ax_bottom.set_xlabel('k value')
    ax_bottom.set_ylabel('time (ms)')
    ax_top.legend(loc='best')
    ax_bottom.legend(loc='best')

    # Show/save plot
    # plt.show()
    plt.savefig(f'k_analysis_first_item_n{n}.png', dpi='figure')
    plt.clf()
    ################
    # Last Item pivot
    sorted_last_ks = [re.search(r'\d+', item).group(0) for item in sorted_last_data.columns]
    sorted_last_y = sorted_last_data.loc[n] / conversion_factor

    reversed_last_ks = [re.search(r'\d+', item).group(0) for item in reversed_last_data.columns]
    reversed_last_y = reversed_last_data.loc[n] / conversion_factor

    random_last_ks = [re.search(r'\d+', item).group(0) for item in random_last_data.columns]
    random_last_y = random_last_data.loc[n] / conversion_factor

    duplicates_last_ks = [re.search(r'\d+', item).group(0) for item in duplicates_last_data.columns]
    duplicates_last_y = duplicates_last_data.loc[n] / conversion_factor

    # Set up plots
    fig, ax = plt.subplots(2, 1, sharex='col', constrained_layout=True, dpi=250)
    ax_top = ax[0]
    ax_bottom = ax[1]

    ax_top.scatter(sorted_last_ks, sorted_last_y, label='Sorted', c=sorted_input_color)
    ax_top.scatter(reversed_last_ks, reversed_last_y, label='Reversed', c=reversed_input_color)
    ax_bottom.scatter(random_last_ks, random_last_y, label='Random', c=random_input_color)
    ax_bottom.scatter(duplicates_last_ks, duplicates_last_y, label='Duplicates', c=duplicates_input_color)

    ax_top.set_title(f'Using Insertion Sort to Finish (last item pivot, n={n})')
    ax_bottom.set_xlabel('k value')
    ax_bottom.set_ylabel('time (ms)')
    ax_top.legend(loc='best')
    ax_bottom.legend(loc='best')

    # Show/save plot
    # plt.show()
    plt.savefig(f'k_analysis_last_item_n{n}.png', dpi='figure')
    plt.clf()

    ################
    # Middle Item pivot
    sorted_mid_ks = [re.search(r'\d+', item).group(0) for item in sorted_mid_data.columns]
    sorted_mid_y = sorted_mid_data.loc[n] / conversion_factor

    reversed_mid_ks = [re.search(r'\d+', item).group(0) for item in reversed_mid_data.columns]
    reversed_mid_y = reversed_mid_data.loc[n] / conversion_factor

    random_mid_ks = [re.search(r'\d+', item).group(0) for item in random_mid_data.columns]
    random_mid_y = random_mid_data.loc[n] / conversion_factor

    duplicates_mid_ks = [re.search(r'\d+', item).group(0) for item in duplicates_mid_data.columns]
    duplicates_mid_y = duplicates_mid_data.loc[n] / conversion_factor

    # Set up plots
    fig, ax = plt.subplots(2, 1, sharex='col', constrained_layout=True, dpi=250)
    ax_top = ax[0]
    ax_bottom = ax[1]

    ax_top.scatter(sorted_mid_ks, sorted_mid_y, label='Sorted', c=sorted_input_color)
    ax_top.scatter(reversed_mid_ks, reversed_mid_y, label='Reversed', c=reversed_input_color)
    ax_bottom.scatter(random_mid_ks, random_mid_y, label='Random', c=random_input_color)
    ax_bottom.scatter(duplicates_mid_ks, duplicates_mid_y, label='Duplicates', c=duplicates_input_color)

    ax_top.set_title(f'Using Insertion Sort to Finish (middle item pivot, n={n})')
    ax_bottom.set_xlabel('k value')
    ax_bottom.set_ylabel('time (ms)')
    ax_top.legend(loc='best')
    ax_bottom.legend(loc='best')

    # Show/save plot
    # plt.show()
    plt.savefig(f'k_analysis_mid_item_n{n}.png', dpi='figure')
    plt.clf()

    ################
    # Median-of-three Item pivot
    sorted_median_ks = [re.search(r'\d+', item).group(0) for item in sorted_median_data.columns]
    sorted_median_y = sorted_median_data.loc[n] / conversion_factor

    reversed_median_ks = [re.search(r'\d+', item).group(0) for item in reversed_median_data.columns]
    reversed_median_y = reversed_median_data.loc[n] / conversion_factor

    random_median_ks = [re.search(r'\d+', item).group(0) for item in random_median_data.columns]
    random_median_y = random_median_data.loc[n] / conversion_factor

    duplicates_median_ks = [re.search(r'\d+', item).group(0) for item in duplicates_median_data.columns]
    duplicates_median_y = duplicates_median_data.loc[n] / conversion_factor

    # Set up plots
    fig, ax = plt.subplots(2, 1, sharex='col', constrained_layout=True, dpi=250)
    ax_top = ax[0]
    ax_bottom = ax[1]

    ax_top.scatter(sorted_median_ks, sorted_median_y, label='Sorted', c=sorted_input_color)
    ax_top.scatter(reversed_median_ks, reversed_median_y, label='Reversed', c=reversed_input_color)
    ax_bottom.scatter(random_median_ks, random_median_y, label='Random', c=random_input_color)
    ax_bottom.scatter(duplicates_median_ks, duplicates_median_y, label='Duplicates', c=duplicates_input_color)

    ax_top.set_title(f'Using Insertion Sort to Finish (median of 3 pivot, n={n})')
    ax_bottom.set_xlabel('k value')
    ax_bottom.set_ylabel('time (ms)')
    ax_top.legend(loc='best')
    ax_bottom.legend(loc='best')

    # Show/save plot
    # plt.show()
    plt.savefig(f'k_analysis_median_item_n{n}.png', dpi='figure')
    plt.clf()

    ################
    # Random Item pivot
    sorted_random_ks = [re.search(r'\d+', item).group(0) for item in sorted_random_data.columns]
    sorted_random_y = sorted_random_data.loc[n] / conversion_factor

    reversed_random_ks = [re.search(r'\d+', item).group(0) for item in reversed_random_data.columns]
    reversed_random_y = reversed_random_data.loc[n] / conversion_factor

    random_random_ks = [re.search(r'\d+', item).group(0) for item in random_random_data.columns]
    random_random_y = random_random_data.loc[n] / conversion_factor

    duplicates_random_ks = [re.search(r'\d+', item).group(0) for item in duplicates_random_data.columns]
    duplicates_random_y = duplicates_random_data.loc[n] / conversion_factor

    # Set up plots
    fig, ax = plt.subplots(2, 1, sharex='col', constrained_layout=True, dpi=250)
    ax_top = ax[0]
    ax_bottom = ax[1]

    ax_top.scatter(sorted_random_ks, sorted_random_y, label='Sorted', c=sorted_input_color)
    ax_top.scatter(reversed_random_ks, reversed_random_y, label='Reversed', c=reversed_input_color)
    ax_bottom.scatter(random_random_ks, random_random_y, label='Random', c=random_input_color)
    ax_bottom.scatter(duplicates_random_ks, duplicates_random_y, label='Duplicates', c=duplicates_input_color)

    ax_top.set_title(f'Using Insertion Sort to Finish (random pivot, n={n})')
    ax_bottom.set_xlabel('k value')
    ax_bottom.set_ylabel('time (ms)')
    ax_top.legend(loc='best')
    ax_bottom.legend(loc='best')

    # Show/save plot
    # plt.show()
    plt.savefig(f'k_analysis_random_item_n{n}.png', dpi='figure')
    plt.clf()


##############################
## K Values as % of input size
##############################


sorted_dataframes = [sorted_first_data, sorted_last_data, sorted_mid_data, sorted_median_data, sorted_random_data]
reversed_dataframes = [reversed_first_data, reversed_last_data, reversed_mid_data, reversed_median_data, reversed_random_data]
random_data_dataframes = [random_first_data, random_last_data, random_mid_data, random_median_data, random_random_data]
duplicates_dataframes = [duplicates_first_data, duplicates_last_data, duplicates_mid_data, duplicates_median_data, duplicates_random_data]
color_first_item = colors['darkorange']
color_last_item = colors['deepskyblue']
color_mid_item = colors['firebrick']
color_median_item = colors['limegreen']
color_random_item = colors['sienna']

first_dataframes = [sorted_first_data, reversed_first_data, random_first_data, duplicates_first_data]
last_dataframes = [sorted_last_data, reversed_last_data, random_last_data, duplicates_last_data]
mid_dataframes = [sorted_mid_data, reversed_mid_data, random_mid_data, duplicates_mid_data]
median_dataframes = [sorted_median_data, reversed_median_data, random_median_data, duplicates_median_data]
random_pivot_dataframes = [sorted_random_data, reversed_random_data, random_random_data, duplicates_random_data]

# Sorted First/last dataframes plot
fig, ax = plt.subplots(1, 2, sharey='row', figsize=(9.6, 5.2), dpi=250, constrained_layout=True)
ax_sorted_first = ax[0]
ax_sorted_first.set_title('Sorted Input; First Item Pivot')
ax_sorted_first.set_xlabel('k-size / size of input (%)')
ax_sorted_first.set_ylabel('% speed improvement')
ax_sorted_first.set_ylim([-25, 100])
ax_sorted_last = ax[1]
ax_sorted_last.set_title('Sorted Input; Last Item Pivot')
ax_sorted_last.set_ylim([-25, 100])

axes = [ax_sorted_first, ax_sorted_last]
dataframes = [sorted_first_data.transpose(), sorted_last_data.transpose()]
item_colors = [color_first_item, color_last_item]
for dataframe, item_color, current_ax in zip(dataframes, item_colors, axes):
    k_values = [int(re.search(r'\d+', index).group(0)) for index in dataframe.index]
    for n in dataframe:
        series = dataframe[n]
        base = series.iloc[0]
        x_data = [min(value/n, 1) * 100 for value in k_values]
        y_data = [((base-item) / base) * 100 for item in series]
        current_ax.scatter(x_data, y_data, s=0.5, c=item_color)

# plt.show()
plt.savefig(f'k_analysis_ratio_sorted_first_last.png', dpi='figure')
plt.clf()

# Sorted Middle/Random
fig, ax = plt.subplots(1, 2, figsize=(9.6, 5.2), dpi=250, constrained_layout=True)
ax_sorted_mid = ax[0]
ax_sorted_mid.set_title('Sorted Input; Middle Item Pivot')
ax_sorted_mid.set_xlabel('k-size / size of input (%)')
ax_sorted_mid.set_ylabel('% speed improvement')
ax_sorted_mid.set_ylim([30, 90])
ax_sorted_random = ax[1]
ax_sorted_random.set_title('Sorted Input; Random Pivot')
ax_sorted_random.set_ylim([60, 100])

axes = [ax_sorted_mid, ax_sorted_random]
dataframes = [sorted_mid_data.transpose(), sorted_random_data.transpose()]
item_colors = [color_mid_item, color_random_item]
for dataframe, item_color, current_ax in zip(dataframes, item_colors, axes):
    k_values = [int(re.search(r'\d+', index).group(0)) for index in dataframe.index]
    for n in dataframe:
        series = dataframe[n]
        base = series.iloc[0]
        x_data = [min(value/n, 1) * 100 for value in k_values]
        y_data = [((base-item) / base) * 100 for item in series]
        current_ax.scatter(x_data, y_data, s=0.5, c=item_color)

# plt.show()
plt.savefig(f'k_analysis_ratio_sorted_mid_random.png', dpi='figure')
plt.clf()

# Median pivot

fig, ax = plt.subplots(1, dpi=250, constrained_layout=True)
ax_sorted_median = ax
ax_sorted_median.set_title('Sorted Input; Median of 3 Pivot')
ax_sorted_median.set_xlabel('k-size / size of input (%)')
ax_sorted_median.set_ylabel('% speed improvement')
ax_sorted_median.set_ylim([-20, 100])

axes = [ax_sorted_median]
dataframes = [sorted_median_data.transpose()]
item_colors = [color_median_item]
for dataframe, item_color, current_ax in zip(dataframes, item_colors, axes):
    k_values = [int(re.search(r'\d+', index).group(0)) for index in dataframe.index]
    for n in dataframe:
        series = dataframe[n]
        base = series.iloc[0]
        x_data = [min(value/n, 1) * 100 for value in k_values]
        y_data = [((base-item) / base) * 100 for item in series]
        current_ax.scatter(x_data, y_data, s=0.5, c=item_color)

# plt.show()
plt.savefig(f'k_analysis_ratio_sorted_median.png', dpi='figure')
plt.clf()



##############################
# K-Values: Reversed Input
##############################

# Reversed First/last dataframes plot
fig, ax = plt.subplots(1, 2, figsize=(9.6, 5.2), dpi=250, constrained_layout=True)
ax_reversed_first = ax[0]
ax_reversed_first.set_title('Reversed Input; First Item Pivot')
ax_reversed_first.set_xlabel('k-size / size of input (%)')
ax_reversed_first.set_ylabel('% speed improvement')
ax_reversed_first.set_ylim([-20, 20])
ax_reversed_last = ax[1]
ax_reversed_last.set_title('Reversed Input; Last Item Pivot')
ax_reversed_last.set_ylim([-40, 20])

axes = [ax_reversed_first, ax_reversed_last]
dataframes = [reversed_first_data.transpose(), reversed_last_data.transpose()]
item_colors = [color_first_item, color_last_item]
for dataframe, item_color, current_ax in zip(dataframes, item_colors, axes):
    k_values = [int(re.search(r'\d+', index).group(0)) for index in dataframe.index]
    for n in dataframe:
        series = dataframe[n]
        base = series.iloc[0]
        x_data = [min(value/n, 1) * 100 for value in k_values]
        y_data = [((base-item) / base) * 100 for item in series]
        current_ax.scatter(x_data, y_data, s=0.5, c=item_color)

# plt.show()
plt.savefig(f'k_analysis_ratio_reversed_first_last.png', dpi='figure')
plt.clf()

# Reversed Middle/Random
fig, ax = plt.subplots(1, 2, figsize=(9.6, 5.2), dpi=250, constrained_layout=True)
ax_reversed_mid = ax[0]
ax_reversed_mid.set_title('Reversed Input; Middle Item Pivot')
ax_reversed_mid.set_xlabel('k-size / size of input (%)')
ax_reversed_mid.set_ylabel('% speed improvement')
ax_reversed_mid.set_ylim([30, 100])
ax_reversed_random = ax[1]
ax_reversed_random.set_title('Reversed Input; Random Pivot')
ax_reversed_random.set_ylim([50, 100])

axes = [ax_reversed_mid, ax_reversed_random]
dataframes = [reversed_mid_data.transpose(), reversed_random_data.transpose()]
item_colors = [color_mid_item, color_random_item]
for dataframe, item_color, current_ax in zip(dataframes, item_colors, axes):
    k_values = [int(re.search(r'\d+', index).group(0)) for index in dataframe.index]
    for n in dataframe:
        series = dataframe[n]
        base = series.iloc[0]
        x_data = [min(value/n, 1) * 100 for value in k_values]
        y_data = [((base-item) / base) * 100 for item in series]
        current_ax.scatter(x_data, y_data, s=0.5, c=item_color)

# plt.show()
plt.savefig(f'k_analysis_ratio_reversed_middle_random.png', dpi='figure')
plt.clf()

# Median pivot

fig, ax = plt.subplots(1, dpi=250, constrained_layout=True)
ax_reversed_median = ax
ax_reversed_median.set_title('Reversed Input; Median of 3 Pivot')
ax_reversed_median.set_xlabel('k-size / size of input (%)')
ax_reversed_median.set_ylabel('% speed improvement')
ax_reversed_median.set_ylim([-25, 100])

axes = [ax_reversed_median]
dataframes = [reversed_median_data.transpose()]
item_colors = [color_median_item]
for dataframe, item_color, current_ax in zip(dataframes, item_colors, axes):
    k_values = [int(re.search(r'\d+', index).group(0)) for index in dataframe.index]
    for n in dataframe:
        series = dataframe[n]
        base = series.iloc[0]
        x_data = [min(value/n, 1) * 100 for value in k_values]
        y_data = [((base-item) / base) * 100 for item in series]
        current_ax.scatter(x_data, y_data, s=0.5, c=item_color)

# plt.show()
plt.savefig(f'k_analysis_ratio_reversed_median.png', dpi='figure')
plt.clf()


##############################
# K-Values: Random Input
##############################

# Random First/last dataframes plot
fig, ax = plt.subplots(1, 2, figsize=(9.6, 5.2), dpi=250, constrained_layout=True)
ax_random_first = ax[0]
ax_random_first.set_title('Random Input; First Item Pivot')
ax_random_first.set_xlabel('k-size / size of input (%)')
ax_random_first.set_ylabel('% speed improvement')
ax_random_first.set_ylim([-20, 60])
ax_random_last = ax[1]
ax_random_last.set_title('Random Input; Last Item Pivot')
ax_random_last.set_ylim([-20, 60])

axes = [ax_random_first, ax_random_last]
dataframes = [random_first_data.transpose(), random_last_data.transpose()]
item_colors = [color_first_item, color_last_item]
for dataframe, item_color, current_ax in zip(dataframes, item_colors, axes):
    k_values = [int(re.search(r'\d+', index).group(0)) for index in dataframe.index]
    for n in dataframe:
        series = dataframe[n]
        base = series.iloc[0]
        x_data = [min(value/n, 1) * 100 for value in k_values]
        y_data = [((base-item) / base) * 100 for item in series]
        current_ax.scatter(x_data, y_data, s=0.5, c=item_color)

# plt.show()
plt.savefig(f'k_analysis_ratio_random_first_last.png', dpi='figure')
plt.clf()

# Random Middle/Median
fig, ax = plt.subplots(1, 2, figsize=(9.6, 5.2), dpi=250, constrained_layout=True)
ax_random_mid = ax[0]
ax_random_mid.set_title('Random Input; Middle Item Pivot')
ax_random_mid.set_xlabel('k-size / size of input (%)')
ax_random_mid.set_ylabel('% speed improvement')
ax_random_mid.set_ylim([-50, 50])
ax_random_median = ax[1]
ax_random_median.set_title('Random Input; Median of 3 Pivot')
ax_random_median.set_ylim([-10, 50])

axes = [ax_random_mid, ax_random_median]
dataframes = [random_mid_data.transpose(), random_median_data.transpose()]
item_colors = [color_mid_item, color_median_item]
for dataframe, item_color, current_ax in zip(dataframes, item_colors, axes):
    k_values = [int(re.search(r'\d+', index).group(0)) for index in dataframe.index]
    for n in dataframe:
        series = dataframe[n]
        base = series.iloc[0]
        x_data = [min(value/n, 1) * 100 for value in k_values]
        y_data = [((base-item) / base) * 100 for item in series]
        current_ax.scatter(x_data, y_data, s=0.5, c=item_color)

# plt.show()
plt.savefig(f'k_analysis_ratio_random_mid_median.png', dpi='figure')
plt.clf()


# Random pivot

fig, ax = plt.subplots(1, dpi=250, constrained_layout=True)
ax_random_random = ax
ax_random_random.set_title('Random Input; Random Pivot')
ax_random_random.set_xlabel('k-size / size of input (%)')
ax_random_random.set_ylabel('% speed improvement')
ax_random_random.set_ylim([50, 100])

axes = [ax_random_random]
dataframes = [random_random_data.transpose()]
item_colors = [color_random_item]
for dataframe, item_color, current_ax in zip(dataframes, item_colors, axes):
    k_values = [int(re.search(r'\d+', index).group(0)) for index in dataframe.index]
    for n in dataframe:
        series = dataframe[n]
        base = series.iloc[0]
        x_data = [min(value/n, 1) * 100 for value in k_values]
        y_data = [((base-item) / base) * 100 for item in series]
        current_ax.scatter(x_data, y_data, s=0.5, c=item_color)

# plt.show()
plt.savefig(f'k_analysis_ratio_random_random.png', dpi='figure')
plt.clf()



##############################
# K-Values: Random Input (zoomed)
##############################

# Random First/last dataframes plot
fig, ax = plt.subplots(1, 2, figsize=(9.6, 5.2), dpi=250, constrained_layout=True)
ax_random_first = ax[0]
ax_random_first.set_title('Random Input; First Item Pivot (zoomed)')
ax_random_first.set_xlabel('k-size / size of input (%)')
ax_random_first.set_ylabel('% speed improvement')
ax_random_first.set_ylim([-10, 40])
ax_random_first.set_xlim([0, 10])
ax_random_last = ax[1]
ax_random_last.set_title('Random Input; Last Item Pivot (zoom)')
ax_random_last.set_ylim([-10, 40])
ax_random_last.set_xlim([0, 10])

axes = [ax_random_first, ax_random_last]
dataframes = [random_first_data.transpose(), random_last_data.transpose()]
item_colors = [color_first_item, color_last_item]
for dataframe, item_color, current_ax in zip(dataframes, item_colors, axes):
    k_values = [int(re.search(r'\d+', index).group(0)) for index in dataframe.index]
    for n in dataframe:
        series = dataframe[n]
        base = series.iloc[0]
        x_data = [min(value/n, 1) * 100 for value in k_values]
        y_data = [((base-item) / base) * 100 for item in series]
        current_ax.scatter(x_data, y_data, s=0.5, c=item_color)

# plt.show()
plt.savefig(f'k_analysis_ratio_random_first_last_zoom.png', dpi='figure')
plt.clf()

# Random Middle/Median
fig, ax = plt.subplots(1, 2, figsize=(9.6, 5.2), dpi=250, constrained_layout=True)
ax_random_mid = ax[0]
ax_random_mid.set_title('Random Input; Middle Item Pivot (zoom)')
ax_random_mid.set_xlabel('k-size / size of input (%)')
ax_random_mid.set_ylabel('% speed improvement')
ax_random_mid.set_ylim([-50, 40])
ax_random_mid.set_xlim([0, 10])
ax_random_median = ax[1]
ax_random_median.set_title('Random Input; Median of 3 Pivot (zoom)')
ax_random_median.set_ylim([-10, 40])
ax_random_median.set_xlim([0, 10])

axes = [ax_random_mid, ax_random_median]
dataframes = [random_mid_data.transpose(), random_median_data.transpose()]
item_colors = [color_mid_item, color_median_item]
for dataframe, item_color, current_ax in zip(dataframes, item_colors, axes):
    k_values = [int(re.search(r'\d+', index).group(0)) for index in dataframe.index]
    for n in dataframe:
        series = dataframe[n]
        base = series.iloc[0]
        x_data = [min(value/n, 1) * 100 for value in k_values]
        y_data = [((base-item) / base) * 100 for item in series]
        current_ax.scatter(x_data, y_data, s=0.5, c=item_color)

# plt.show()
plt.savefig(f'k_analysis_ratio_random_mid_median_zoom.png', dpi='figure')
plt.clf()

# Random pivot

fig, ax = plt.subplots(1, dpi=250, constrained_layout=True)
ax_random_random = ax
ax_random_random.set_title('Random Input; Random Pivot (zoom)')
ax_random_random.set_xlabel('k-size / size of input (%)')
ax_random_random.set_ylabel('% speed improvement')
ax_random_random.set_ylim([70, 100])
ax_random_random.set_xlim([0, 20])

axes = [ax_random_random]
dataframes = [random_random_data.transpose()]
item_colors = [color_random_item]
for dataframe, item_color, current_ax in zip(dataframes, item_colors, axes):
    k_values = [int(re.search(r'\d+', index).group(0)) for index in dataframe.index]
    for n in dataframe:
        series = dataframe[n]
        base = series.iloc[0]
        x_data = [min(value/n, 1) * 100 for value in k_values]
        y_data = [((base-item) / base) * 100 for item in series]
        current_ax.scatter(x_data, y_data, s=0.5, c=item_color)

# plt.show()
plt.savefig(f'k_analysis_ratio_random_random_zoom.png', dpi='figure')
plt.clf()

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


fig, ax = plt.subplots(1, dpi=250, constrained_layout=True)
ax.set_title('Heap Sort Performance')
ax.set_xlabel('input size')
ax.set_ylabel('time (ms)')

ax.scatter(heap_sorted_x, heap_sorted_y, s=0.8, label='Sorted')
ax.scatter(heap_reversed_x, heap_reversed_y, s=0.8, label='Reversed')
ax.scatter(heap_random_x, heap_random_y, s=0.8, label='Random')
ax.scatter(heap_duplicates_x, heap_duplicates_x, s=0.8, label='Duplicates')

plt.show()
