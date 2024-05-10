USE team5_projectdb;

DROP TABLE IF EXISTS q1_results;
CREATE EXTERNAL TABLE q1_results (
    year INT,
    day INT,
    ransomware_addresses INT,
    white_addresses INT
)
ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
LOCATION 'project/hive/warehouse/q1';

-- to not display table names with column names
SET hive.resultset.use.unique.column.names = false;

WITH ransomware_daily AS (
    SELECT year, day, COUNT(DISTINCT address) AS ransomware_addresses
    FROM addresses_part
    WHERE label != 'white'
    GROUP BY year, day
),
white_daily AS (
    SELECT year, day, COUNT(DISTINCT address) AS white_addresses
    FROM addresses_part
    WHERE label = 'white'
    GROUP BY year, day
)
INSERT INTO q1_results
SELECT ransomware.year, ransomware.day, ransomware.ransomware_addresses, white.white_addresses
FROM ransomware_daily ransomware
JOIN white_daily white
ON ransomware.year = white.year AND ransomware.day = white.day;

INSERT OVERWRITE DIRECTORY 'project/output/q1'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
SELECT * FROM q1_results;