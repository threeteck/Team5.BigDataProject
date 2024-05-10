DROP DATABASE IF EXISTS team5_projectdb CASCADE;
CREATE DATABASE team5_projectdb LOCATION "project/hive/warehouse";
USE team5_projectdb;

CREATE EXTERNAL TABLE addresses STORED AS AVRO LOCATION 'project/warehouse/bitcoin_heist' TBLPROPERTIES ('avro.schema.url'='project/warehouse/avsc/bitcoin_heist.avsc');

SELECT * FROM addresses LIMIT 10;

SET hive.exec.dynamic.partition=true;
SET hive.exec.dynamic.partition.mode=nonstrict;

CREATE EXTERNAL TABLE IF NOT EXISTS addresses_part (
    address varchar(64),
    day int,
    length int,
    weight float,
    count int,
    looped int,
    neighbors int,
    income bigint,
    label varchar(64)
) 
PARTITIONED BY (year int)
CLUSTERED BY (label) into 6 buckets
STORED AS AVRO LOCATION 'project/hive/warehouse/addresses_part'
TBLPROPERTIES ('AVRO.COMPRESS'='SNAPPY');

INSERT OVERWRITE TABLE addresses_part
PARTITION (year)
SELECT address, day, length, weight, count, looped, neighbors, income, label, year
FROM addresses;

DROP TABLE IF EXISTS addresses;

SELECT * FROM addresses_part LIMIT 10;