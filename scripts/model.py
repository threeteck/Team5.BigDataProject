"""
Pipeline to preprocess and classify addresses dataset using
RandomForest and Logistic Regression with hyperparameter tuning.
"""

from datetime import datetime, timedelta
from calendar import monthrange
import math
import os
from pyspark.sql import SparkSession, functions as F, types as T
from pyspark.ml import Pipeline, Transformer
from pyspark.ml.feature import VectorAssembler, MinMaxScaler, StringIndexer
from pyspark.ml.feature import PCA
from pyspark.ml.classification import RandomForestClassifier
from pyspark.ml.classification import LogisticRegression
from pyspark.ml.tuning import ParamGridBuilder, CrossValidator
from pyspark.ml.evaluation import MulticlassClassificationEvaluator


class FeatureEngineeringTransformer(Transformer):
    """A Transformer that applies feature engineering to the dataset."""

    def _transform(self, dataset):
        dataset = dataset.withColumn("income",
                                     F.col("income") / 100000000)

        # Add high-volume transactions
        high_volume_threshold = 10  # 10 BTC
        dataset = dataset.withColumn("high_volume",
                                     F.when(F.col("income") >
                                            high_volume_threshold, 1)
                                     .otherwise(0))

        # Add low-volume transactions
        low_volume_threshold = 1
        dataset = dataset.withColumn("low_volume",
                                     F.when(F.col("income") <
                                            low_volume_threshold, 1)
                                     .otherwise(0))

        dataset = dataset.withColumn("income_per_neighbor",
                                     F.when(F.col("neighbors") == 0, 0)
                                      .otherwise(F.col("income") /
                                                 F.col("neighbors")))

        total_income_per_day = dataset.groupBy("year", "day")\
                                      .agg(F.sum("income")
                                           .alias("total_daily_income"))
        dataset = dataset.join(total_income_per_day,
                               ["year", "day"], how="left")
        dataset = dataset.withColumn("relative_importance",
                                     F.when(F.col("total_daily_income") == 0,
                                            0)
                                      .otherwise(
                                           F.col("income") /
                                           F.col("total_daily_income"))
                                     )

        dataset = dataset.drop('total_daily_income')

        return dataset


class AggregationTransformer(Transformer):
    """A Transformer that aggregates address-level information."""

    def _transform(self, dataset):
        aggregated_df = dataset.groupBy("address").agg(
            F.last("year").alias("year"),
            F.last("day").alias("day"),
            F.count("*").alias("reuse_count"),
            F.sum("income").alias("total_income"),
            F.mean("income").alias("avg_income"),
            F.mean("length").alias("avg_length"),
            F.mean("weight").alias("avg_weight"),
            F.mean("count").alias("avg_count"),
            F.sum("looped").alias("total_looped"),
            F.mean("looped").alias("avg_looped"),
            F.mean("neighbors").alias("avg_neighbors"),
            F.sum("high_volume").alias("high_volume_count"),
            F.sum("low_volume").alias("low_volume_count"),
            F.mean("income_per_neighbor").alias("avg_income_per_neighbor"),
            F.mean("relative_importance").alias("avg_relative_importance"),
            F.last("label").alias("label"),
            F.last("indexed_label").alias("indexed_label")
        )

        aggregated_df = aggregated_df.fillna(0, subset=
                                             ["avg_relative_importance",
                                              "avg_income_per_neighbor"])

        return aggregated_df


class SinCosDateTransformer(Transformer):
    """A Transformer that encodes date information into sin/cos features."""

    def _transform(self, dataset):
        def to_sin_cos(value, period):
            radians = 2 * math.pi * value / period
            return (math.sin(radians), math.cos(radians))

        def extract_month_day(year, day):
            base_date = datetime(year, 1, 1)
            target_date = base_date + timedelta(days=day - 1)
            return target_date.month, target_date.day

        def days_in_month(year, month):
            return monthrange(year, month)[1]

        month_day_udf = F.udf(extract_month_day,
                              T.StructType([
                                T.StructField("month", T.IntegerType(), False),
                                T.StructField("day", T.IntegerType(), False)
                              ]))

        def sin_cos_days(year, month, day):
            days_in_current_month = days_in_month(year, month)
            return to_sin_cos(day, days_in_current_month)

        sin_cos_days_udf = F.udf(sin_cos_days,
                                 T.StructType([
                                    T.StructField("sin",
                                                  T.DoubleType(), False),
                                    T.StructField("cos",
                                                  T.DoubleType(), False)
                                 ]))

        def sin_cos_month(month):
            return to_sin_cos(month, 12)

        sin_cos_month_udf = F.udf(sin_cos_month,
                                  T.StructType([
                                    T.StructField("sin",
                                                  T.DoubleType(), False),
                                    T.StructField("cos",
                                                  T.DoubleType(), False)
                                  ]))

        # Extract month and day
        dataset = dataset.withColumn("month_day",
                                     month_day_udf(F.col("year"),
                                                   F.col("day")))

        # Encode month into sin and cos
        dataset = dataset.withColumn("monthsin_cos",
                                     sin_cos_month_udf(
                                         F.col("month_day.month")
                                                      ))
        dataset = dataset.withColumn("monthsin", F.col("monthsin_cos.sin")) \
                         .withColumn("monthcos", F.col("monthsin_cos.cos"))

        # Encode day into sin and cos based on the number of days in the month
        dataset = dataset.withColumn("daysin_cos",
                                     sin_cos_days_udf(F.col("year"),
                                                      F.col("month_day.month"),
                                                      F.col("month_day.day")))
        dataset = dataset.withColumn("daysin", F.col("daysin_cos.sin")) \
                         .withColumn("daycos", F.col("daysin_cos.cos"))

        # Drop intermediate columns
        dataset = dataset.drop("day", "month_day",
                               "monthsin_cos", "daysin_cos")
        return dataset


class DropAddressTransformer(Transformer):
    """A Transformer that drops the address column."""

    def _transform(self, dataset):
        return dataset.drop('address')


def create_pipeline():
    """Creates the feature engineering and aggregation pipeline."""

    feature_engineering = FeatureEngineeringTransformer()
    aggregation = AggregationTransformer()
    sincos_date = SinCosDateTransformer()
    drop_address = DropAddressTransformer()

    feature_columns = ['year',
                       'reuse_count',
                       'total_income',
                       'avg_income',
                       'avg_length',
                       'avg_weight',
                       'avg_count',
                       'total_looped',
                       'avg_looped',
                       'avg_neighbors',
                       'high_volume_count',
                       'low_volume_count',
                       'avg_income_per_neighbor',
                       'avg_relative_importance',
                       'monthsin',
                       'monthcos',
                       'daysin',
                       'daycos']

    assembler = VectorAssembler(inputCols=feature_columns, outputCol="features")
    scaler = MinMaxScaler(inputCol="features", outputCol="scaled_features")

    pipeline_res = Pipeline(stages=[feature_engineering, aggregation,
                                    sincos_date, drop_address,
                                    assembler, scaler])
    return pipeline_res


TEAM = "team5"
WAREHOUSE = "project/hive/warehouse"
URL = "thrift://hadoop-02.uni.innopolis.ru:9883"
PARALLEL = 6
SEED = 42

print("Creating spark context... ", end="", flush=True)
spark = SparkSession.builder\
        .appName(f"{TEAM} - spark ML")\
        .master("yarn")\
        .config('spark.logConf', 'true')\
        .config("hive.metastore.uris", URL)\
        .config("spark.sql.warehouse.dir", WAREHOUSE)\
        .config("spark.executor.instances", PARALLEL)\
        .config("spark.sql.avro.compression.codec", "snappy")\
        .enableHiveSupport()\
        .getOrCreate()
sc = spark.sparkContext
sc.setLogLevel("ERROR")
print("Done!\n")

print("Loading table... ", end="", flush=True)
addresses = spark.read.format("avro").table('team5_projectdb.addresses_part')
print("Done!")

print("Preprocessing label and spliting data... ", end="", flush=True)
indexer = StringIndexer(inputCol="label", outputCol="indexed_label")
addresses_indexed = indexer.fit(addresses).transform(addresses)

train_data, test_data = addresses_indexed.randomSplit([0.6, 0.4], seed=42)
print("Done!", flush=True)

print("Preprocessing features... ", end="", flush=True)
pipeline = create_pipeline()
pipeline_model = pipeline.fit(train_data)
train_data_transformed = pipeline_model.transform(train_data)
test_data_transformed = pipeline_model.transform(test_data)

train_data_final = train_data_transformed\
                    .select("indexed_label", "scaled_features")\
                    .withColumnRenamed("scaled_features", "features")
test_data_final = test_data_transformed\
                    .select("indexed_label", "scaled_features")\
                    .withColumnRenamed("scaled_features", "features")
print("Done!")


def run(command):
    """Execute a shell command."""
    return os.popen(command).read()


def save_train_test_data(train_split, test_split, suffix=None):
    """Save train and test data as JSON files."""
    def append_suffix(name, suffix):
        return name + (f"_{suffix}" if suffix is not None else "")

    train_name = append_suffix("train", suffix)
    test_name = append_suffix("test", suffix)

    train_split.select("features", "indexed_label")\
        .coalesce(1)\
        .write\
        .mode("overwrite")\
        .format("json")\
        .save(f"project/data/{train_name}")

    run(f"hdfs dfs -cat project/data/{train_name}/*.json " +
        f"> data/{train_name}.json")

    test_split.select("features", "indexed_label")\
        .coalesce(1)\
        .write\
        .mode("overwrite")\
        .format("json")\
        .save(f"project/data/{test_name}")

    run(f"hdfs dfs -cat project/data/{test_name}/*.json " +
        f"> data/{test_name}.json")


print("Saving data... ", end="", flush=True)
save_train_test_data(train_data_final, test_data_final)
print("Done!")


def save_model_and_predictions(model, model_name, test_data_set, save=True):
    """Save the model and its predictions."""

    if save:
        model_path = f"project/models/{model_name}"
        model.write().overwrite().save(model_path)
        run(f"hdfs dfs -get {model_path} models/{model_name}")

    predictions = model.transform(test_data_set)
    predictions.select("indexed_label", "prediction")\
        .coalesce(1)\
        .write\
        .mode("overwrite")\
        .format("csv")\
        .option("sep", ",")\
        .option("header", "true")\
        .save(f"project/output/{model_name}_predictions.csv")

    run(f"hdfs dfs -cat project/output/{model_name}_predictions.csv/*.csv " +
        f"> output/{model_name}_predictions.csv")
    return predictions


def load_model(model_class, model_name):
    """Load a previously saved model."""

    model_path = f"project/models/{model_name}"
    model = model_class.load(model_path)
    return model


# Utility function to evaluate models
def evaluate_model(predictions, model_name):
    """Evaluate a model's predictions using various metrics."""

    evaluator = MulticlassClassificationEvaluator(labelCol="indexed_label",
                                                  predictionCol="prediction")
    accuracy = evaluator.evaluate(predictions,
                                  {evaluator.metricName: "accuracy"})
    precision = evaluator.evaluate(predictions,
                                   {evaluator.metricName: "weightedPrecision"})
    recall = evaluator.evaluate(predictions,
                                {evaluator.metricName: "weightedRecall"})
    f1_score = evaluator.evaluate(predictions,
                                  {evaluator.metricName: "f1"})
    print(f"{model_name} Results - Accuracy: {accuracy:.4f}, "
          + f"Precision: {precision:.4f}, Recall: {recall:.4f}, "
          + f"F1-Score: {f1_score:.4f}")
    return accuracy, precision, recall, f1_score


print("\n=== Training and evaluating default models ===", end="\n\n", flush=True)
# RandomForestClassifier - Default Parameters
rf_default = RandomForestClassifier(featuresCol="features",
                                    labelCol="indexed_label")
rf_default_model = rf_default.fit(train_data_final)
rf_default_predictions = save_model_and_predictions(rf_default_model,
                                                    "model_rf_default",
                                                    test_data_final)
RF_DEFAULT_NAME = "RandomForestClassifier (Default)"
rf_default_metrics = evaluate_model(rf_default_predictions,
                                    RF_DEFAULT_NAME)

# LogisticRegression - Default Parameters
lr_default = LogisticRegression(featuresCol="features",
                                labelCol="indexed_label")
lr_default_model = lr_default.fit(train_data_final)
lr_default_predictions = save_model_and_predictions(lr_default_model,
                                                    "model_lr_default",
                                                    test_data_final)
LR_DEFAULT_NAME = "LogisticRegression (Default)"
lr_default_metrics = evaluate_model(lr_default_predictions,
                                    LR_DEFAULT_NAME)


print("\n=== Hyperparameter tuning ===", end="\n\n", flush=True)

# RandomForestClassifier - Hyperparameter Tuning
rf = RandomForestClassifier(featuresCol="features", labelCol="indexed_label")
rf_param_grid = (ParamGridBuilder()
                 .addGrid(rf.numTrees, [50, 100, 200])
                 .addGrid(rf.maxDepth, [5, 10, 15])
                 .build())
rf_crossval = CrossValidator(estimator=rf,
                             estimatorParamMaps=rf_param_grid,
                             evaluator=MulticlassClassificationEvaluator(
                                 labelCol="indexed_label",
                                 predictionCol="prediction",
                                 metricName="weightedRecall"),
                             parallelism=PARALLEL,
                             numFolds=3)

rf_cv_model = rf_crossval.fit(train_data_final)
rf_best_model = rf_cv_model.bestModel
rf_predictions = save_model_and_predictions(rf_best_model,
                                            "model_rf_tuned",
                                            test_data_final)
RF_NAME = "RandomForestClassifier (Tuned)"
rf_metrics = evaluate_model(rf_predictions,
                            RF_NAME)

# LogisticRegression - Hyperparameter Tuning
lr = LogisticRegression(featuresCol="features", labelCol="indexed_label")
lr_param_grid = (ParamGridBuilder()
                 .addGrid(lr.regParam, [0.01, 0.1, 0.5])
                 .addGrid(lr.elasticNetParam, [0.0, 0.25, 0.5])
                 .build())
lr_crossval = CrossValidator(estimator=lr,
                             estimatorParamMaps=lr_param_grid,
                             evaluator=MulticlassClassificationEvaluator(
                                 labelCol="indexed_label",
                                 predictionCol="prediction",
                                 metricName="weightedRecall"),
                             parallelism=PARALLEL,
                             numFolds=3)

lr_cv_model = lr_crossval.fit(train_data_final)
lr_best_model = lr_cv_model.bestModel
lr_predictions = save_model_and_predictions(lr_best_model,
                                            "model_lr_tuned",
                                            test_data_final)
LR_NAME = "LogisticRegression (Tuned)"
lr_metrics = evaluate_model(lr_predictions,
                            LR_NAME)

print("\n=== Hyperparameters results ===", end="\n\n", flush=True)

print("Logistic Regression Default parameters")
print(f"\t regParam = {lr_default_model.getRegParam()}")
print("\t elasticNetParam = " +
      str(lr_default_model.getElasticNetParam()))

print("\nLogistic Regression Tuned parameters")
print(f"\t regParam = {lr_best_model.getRegParam()}")
print("\t elasticNetParam = " +
      str(lr_best_model.getElasticNetParam()))

print("\nRandom Forest Default parameters")
print(f"\t NumTrees = {rf_default_model.getNumTrees}")
print("\t MaxDepth = " +
      str(rf_default_model.getMaxDepth()))

print("\nRandom Forest Tuned parameters")
print(f"\t NumTrees = {rf_best_model.getNumTrees}")
print("\t MaxDepth = " +
      str(rf_best_model.getMaxDepth()))

print("\n=== Applying PCA ===", end="\n\n", flush=True)
# PCA Transformation
pca = PCA(k=10, inputCol="features", outputCol="pca_features")
train_data_pca = pca.fit(train_data_final).transform(train_data_final)
test_data_pca = pca.fit(test_data_final).transform(test_data_final)

train_data_pca = train_data_pca.select("indexed_label", "pca_features")\
                               .withColumnRenamed("pca_features", "features")
test_data_pca = test_data_pca.select("indexed_label", "pca_features")\
                             .withColumnRenamed("pca_features", "features")

save_train_test_data(train_data_pca, test_data_pca, suffix='pca')

# Refit RandomForestClassifier with PCA Features
rf_best_pca = RandomForestClassifier(featuresCol="features",
                                     labelCol="indexed_label",
                                     numTrees=rf_best_model.getNumTrees,
                                     maxDepth=rf_best_model.getMaxDepth())
rf_pca_model = rf_best_pca.fit(train_data_pca)
rf_pca_predictions = save_model_and_predictions(rf_pca_model,
                                                "model_rf_tuned_pca",
                                                test_data_pca)
RF_PCA_NAME = "RandomForestClassifier (Tuned + PCA)"
rf_pca_metrics = evaluate_model(rf_pca_predictions, RF_PCA_NAME)

# Refit LogisticRegression with PCA Features
lr_best_pca = LogisticRegression(featuresCol="features",
                                 labelCol="indexed_label",
                                 regParam=lr_best_model.getRegParam(),
                                 elasticNetParam=lr_best_model
                                 .getElasticNetParam())
lr_pca_model = lr_best_pca.fit(train_data_pca)
lr_pca_predictions = save_model_and_predictions(lr_pca_model,
                                                "model_lr_tuned_pca",
                                                test_data_pca)
LR_PCA_NAME = "LogisticRegression (Tuned + PCA)"
lr_pca_metrics = evaluate_model(lr_pca_predictions, LR_PCA_NAME)

print("\n=== Comparision table ===", end="\n\n", flush=True)

# Create Comparison Table
model_comparisons = [
    [RF_DEFAULT_NAME, str(rf_default_model), *rf_default_metrics],
    [RF_NAME, str(rf_best_model), *rf_metrics],
    [RF_PCA_NAME, str(rf_pca_model), *rf_pca_metrics],
    [LR_DEFAULT_NAME, str(lr_default_model), *lr_default_metrics],
    [LR_NAME, str(lr_best_model), *lr_metrics],
    [LR_PCA_NAME, str(lr_pca_model), *lr_pca_metrics]
]

comparison_df = spark.createDataFrame(model_comparisons,
                                      ["model", "model_details",
                                       "accuracy", "precision",
                                       "recall", "f1_score"])
comparison_df.show()

# Save Comparison Table
comparison_df.coalesce(1)\
    .write\
    .mode("overwrite")\
    .format("csv")\
    .option("sep", ",")\
    .option("header", "true")\
    .save("project/output/evaluation.csv")

run("hdfs dfs -cat project/output/evaluation.csv/*.csv " +
    "> output/evaluation.csv")

spark.stop()
