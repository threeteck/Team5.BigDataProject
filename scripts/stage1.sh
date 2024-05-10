#!/bin/bash

printf "Creating and importing table... "
python3 scripts/stage1_scripts/build_projectdb.py
printf "Done!\n"

password=$(head -n 1 secrets/.psql.pass)

printf "Importing database to HDFS... "
hdfs dfs -rm -R -skipTrash project/warehouse/* > /dev/null 2> /dev/null
sqoop import-all-tables \
    --connect jdbc:postgresql://hadoop-04.uni.innopolis.ru/team5_projectdb \
    --username team5 \
    --password $password \
    --warehouse-dir=project/warehouse \
    --compress \
    --as-avrodatafile \
    --compression-codec=snappy \
    --m 1 > /dev/null
    
mv *.java output/
mv *.avsc output/

printf "Done!\n\n"