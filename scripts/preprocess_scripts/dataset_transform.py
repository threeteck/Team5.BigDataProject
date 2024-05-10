"""
Script to preprocess and balance the Bitcoin Heist dataset.
"""

import pandas as pd

DATA_PATH = 'data/BitcoinHeistData.csv'
df = pd.read_csv(DATA_PATH)

# Drop null values and duplicates
df_clean = df.dropna().drop_duplicates()
df_clean['income'] = df_clean['income'].astype('int64')

# Keep only the top 6 labels and drop others
top_6_labels = df_clean['label'].value_counts().nlargest(6).index
df_top_6 = df_clean[df_clean['label'].isin(top_6_labels)]

# Count non-white samples and calculate the number of white samples to include
non_white_samples = df_top_6[df_top_6['label'] != 'white']
num_non_white = len(non_white_samples)
num_white_to_include = 100000 - num_non_white

# Undersample white addresses to balance the dataset
white_samples = df_top_6[df_top_6['label'] == 'white']\
                .sample(n=num_white_to_include, random_state=42)

# Combine non-white and sampled white addresses
balanced_df = pd.concat([non_white_samples, white_samples])

# Save the balanced dataset to a CSV file
balanced_df.to_csv('data/balanced_bitcoin_heist.csv', index=False)
