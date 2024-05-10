#!/bin/bash

hdfs dfs -rm -R -skipTrash project/hive/warehouse/* > /dev/null 2> /dev/null
hdfs dfs -mkdir -p project/warehouse/avsc
hdfs dfs -put -f output/*.avsc project/warehouse/avsc

password=$(head -n 1 secrets/.hive.pass)

printf "\nCreating hive table... "
beeline -u jdbc:hive2://hadoop-03.uni.innopolis.ru:10001 -n team5 -p $password -f sql/create_hive_table.hql > output/hive_results.txt 2> /dev/null
printf "Done!\n"


printf "\n=== Performing EDA ===\n\n"

# Insight 1
printf "Running query 1... "
hdfs dfs -rm -R -skipTrash project/output/q1 > /dev/null 2> /dev/null
hdfs dfs -rm -R -skipTrash project/hive/warehouse/q1 > /dev/null 2> /dev/null
echo "year,day,ransomware_addresses,white_addresses" > output/q1.csv
beeline -u jdbc:hive2://hadoop-03.uni.innopolis.ru:10001 -n team5 -p $password -f sql/q1.hql > /dev/null 2> /dev/null
hdfs dfs -cat project/output/q1/* >> output/q1.csv
printf "Done!\n"

# Insight 2
printf "Running query 2... "
hdfs dfs -rm -R -skipTrash project/output/q2 > /dev/null 2> /dev/null
hdfs dfs -rm -R -skipTrash project/hive/warehouse/q2 > /dev/null 2> /dev/null
echo "year,label,num_addresses" > output/q2.csv
beeline -u jdbc:hive2://hadoop-03.uni.innopolis.ru:10001 -n team5 -p $password -f sql/q2.hql > /dev/null 2> /dev/null
hdfs dfs -cat project/output/q2/* >> output/q2.csv
printf "Done!\n"

# Insight 3
printf "Running query 3... "
hdfs dfs -rm -R -skipTrash project/output/q3 > /dev/null 2> /dev/null
hdfs dfs -rm -R -skipTrash project/hive/warehouse/q3 > /dev/null 2> /dev/null
echo "label,address,reuse_count" > output/q3.csv
beeline -u jdbc:hive2://hadoop-03.uni.innopolis.ru:10001 -n team5 -p $password -f sql/q3.hql > /dev/null 2> /dev/null
hdfs dfs -cat project/output/q3/* >> output/q3.csv
printf "Done!\n"

# Insight 4
printf "Running query 4... "
hdfs dfs -rm -R -skipTrash project/output/q4 > /dev/null 2> /dev/null
hdfs dfs -rm -R -skipTrash project/hive/warehouse/q4 > /dev/null 2> /dev/null
echo "label,address,income" > output/q4.csv
beeline -u jdbc:hive2://hadoop-03.uni.innopolis.ru:10001 -n team5 -p $password -f sql/q4.hql > /dev/null 2> /dev/null
hdfs dfs -cat project/output/q4/* >> output/q4.csv
printf "Done!\n"

# Insight 5
printf "Running query 5... "
hdfs dfs -rm -R -skipTrash project/output/q5 > /dev/null 2> /dev/null
hdfs dfs -rm -R -skipTrash project/hive/warehouse/q5 > /dev/null 2> /dev/null
#echo "label,year,avg_length,avg_looped" > output/q5.csv
echo "label,address,length,looped" > output/q5.csv
beeline -u jdbc:hive2://hadoop-03.uni.innopolis.ru:10001 -n team5 -p $password -f sql/q5.hql > /dev/null 2> /dev/null
hdfs dfs -cat project/output/q5/* >> output/q5.csv
printf "Done!\n"

# Insight 7
printf "Running query 7... "
hdfs dfs -rm -R -skipTrash project/output/q7 > /dev/null 2> /dev/null
hdfs dfs -rm -R -skipTrash project/hive/warehouse/q7 > /dev/null 2> /dev/null
echo "label,address,income" > output/q7.csv
beeline -u jdbc:hive2://hadoop-03.uni.innopolis.ru:10001 -n team5 -p $password -f sql/q7.hql > /dev/null 2> /dev/null
hdfs dfs -cat project/output/q7/* >> output/q7.csv
printf "Done!\n\n"