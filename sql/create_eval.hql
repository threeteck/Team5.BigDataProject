USE team5_projectdb;

DROP TABLE IF EXISTS model_lr_default_predictions;
CREATE EXTERNAL TABLE model_lr_default_predictions (
    indexed_label INT,
    prediction INT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/output/model_lr_default_predictions.csv'
TBLPROPERTIES ("skip.header.line.count"="1");

DROP TABLE IF EXISTS model_lr_tuned_predictions;
CREATE EXTERNAL TABLE model_lr_tuned_predictions (
    indexed_label INT,
    prediction INT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/output/model_lr_tuned_predictions.csv'
TBLPROPERTIES ("skip.header.line.count"="1");

DROP TABLE IF EXISTS model_lr_tuned_pca_predictions;
CREATE EXTERNAL TABLE model_lr_tuned_pca_predictions (
    indexed_label INT,
    prediction INT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/output/model_lr_tuned_pca_predictions.csv'
TBLPROPERTIES ("skip.header.line.count"="1");

DROP TABLE IF EXISTS model_rf_default_predictions;
CREATE EXTERNAL TABLE model_rf_default_predictions (
    indexed_label INT,
    prediction INT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/output/model_rf_default_predictions.csv'
TBLPROPERTIES ("skip.header.line.count"="1");

DROP TABLE IF EXISTS model_rf_tuned_predictions;
CREATE EXTERNAL TABLE model_rf_tuned_predictions (
    indexed_label INT,
    prediction INT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/output/model_rf_tuned_predictions.csv'
TBLPROPERTIES ("skip.header.line.count"="1");

DROP TABLE IF EXISTS model_rf_tuned_pca_predictions;
CREATE EXTERNAL TABLE model_rf_tuned_pca_predictions (
    indexed_label INT,
    prediction INT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/output/model_rf_tuned_pca_predictions.csv'
TBLPROPERTIES ("skip.header.line.count"="1");

DROP TABLE IF EXISTS evaluation;
CREATE EXTERNAL TABLE evaluation (
    `model` STRING,
    `model_details` STRING,
    `accuracy` DOUBLE,
    `precision` DOUBLE,
    `recall` DOUBLE,
    `f1_score` DOUBLE
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'project/output/evaluation.csv'
TBLPROPERTIES ("skip.header.line.count"="1");