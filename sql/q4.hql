USE team5_projectdb;

DROP TABLE IF EXISTS q4_results;
CREATE EXTERNAL TABLE q4_results (
    label STRING,
    address STRING,
    income BIGINT
)
ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
LOCATION 'project/hive/warehouse/q4';

SET hive.resultset.use.unique.column.names = false;

INSERT INTO q4_results
SELECT label,
       address,
       income
FROM addresses_part;

INSERT OVERWRITE DIRECTORY 'project/output/q4'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
SELECT * FROM q4_results;
