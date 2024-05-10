USE team5_projectdb;

DROP TABLE IF EXISTS q5_results;
CREATE EXTERNAL TABLE q5_results (
    label STRING,
    address STRING,
    length int,
    looped int
)
ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
LOCATION 'project/hive/warehouse/q5';

SET hive.resultset.use.unique.column.names = false;

INSERT INTO q5_results
SELECT label, address, length, looped
FROM addresses_part
ORDER BY label;

INSERT OVERWRITE DIRECTORY 'project/output/q5'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
SELECT * FROM q5_results;
