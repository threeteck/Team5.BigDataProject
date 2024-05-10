USE team5_projectdb;

DROP TABLE IF EXISTS q2_results;
CREATE EXTERNAL TABLE q2_results (
    year INT,
    label STRING,
    num_addresses INT
)
ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
LOCATION 'project/hive/warehouse/q2';

SET hive.resultset.use.unique.column.names = false;

INSERT INTO q2_results
SELECT year, label, COUNT(DISTINCT address) AS num_addresses
FROM addresses_part
WHERE label != 'white'
GROUP BY year, label
ORDER BY year, label;

INSERT OVERWRITE DIRECTORY 'project/output/q2'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
SELECT * FROM q2_results;
