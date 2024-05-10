#!/bin/bash
pip install -q tqdm

rm -rf scripts/.ipynb_checkpoints/ > /dev/null 2> /dev/null
rm -rf scripts/preprocess_scripts/.ipynb_checkpoints/ > /dev/null 2> /dev/null
rm -rf scripts/stage1_scripts/.ipynb_checkpoints/ > /dev/null 2> /dev/null

url="https://archive.ics.uci.edu/static/public/526/bitcoinheistransomwareaddressdataset.zip"

printf "\n=== Downloading dataset ===\n\n"
rm -f data/BitcoinHeistData.csv
printf "Downloading... "
wget "$url" -q -O data/data.zip
printf "Done!\n"
printf "Unzipping archive... "
unzip -qq data/data.zip -d data/
rm data/data.zip
printf "Done!\n"

printf "Preprocessing dataset... "
python3 scripts/preprocess_scripts/dataset_transform.py
printf "Done\n\n"

