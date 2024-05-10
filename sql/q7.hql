USE team5_projectdb;

DROP TABLE IF EXISTS q7_results;
CREATE EXTERNAL TABLE q7_results (
    label STRING,
    address STRING,
    income BIGINT
)
ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
LOCATION 'project/hive/warehouse/q7';

SET hive.resultset.use.unique.column.names = false;

WITH quartiles AS (
    SELECT
        PERCENTILE(income, 0.25) AS Q1,
        PERCENTILE(income, 0.75) AS Q3
    FROM addresses_part
),
iqr_values AS (
    SELECT
        Q1,
        Q3,
        Q3 - Q1 AS IQR,
        Q1 - 1.5 * (Q3 - Q1) AS lower_bound,
        Q3 + 1.5 * (Q3 - Q1) AS upper_bound
    FROM quartiles
)
INSERT INTO q7_results
SELECT
    label,
    address,
    income
FROM addresses_part, iqr_values
WHERE income BETWEEN lower_bound AND upper_bound;

INSERT OVERWRITE DIRECTORY 'project/output/q7'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
SELECT * FROM q7_results;
