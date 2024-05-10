#!/bin/bash
password=$(head -n 1 secrets/.hive.pass)

printf "\nCreating hive evaluation and prediction tables... "
beeline -u jdbc:hive2://hadoop-03.uni.innopolis.ru:10001 -n team5 -p $password -f sql/create_eval.hql > /dev/null 2> /dev/null
printf "Done!\n\n"