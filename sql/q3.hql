USE team5_projectdb;

DROP TABLE IF EXISTS q3_results;
CREATE EXTERNAL TABLE q3_results (
    label STRING,
    address STRING,
    reuse_count INT
)
ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
LOCATION 'project/hive/warehouse/q3';

SET hive.resultset.use.unique.column.names = false;

INSERT INTO q3_results
SELECT label, address, COUNT(DISTINCT CONCAT(year, '-', day)) AS reuse_count
FROM addresses_part
GROUP BY label, address
ORDER BY reuse_count DESC;

INSERT OVERWRITE DIRECTORY 'project/output/q3'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
SELECT * FROM q3_results;
