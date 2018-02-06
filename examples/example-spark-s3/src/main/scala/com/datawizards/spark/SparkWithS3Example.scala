package com.datawizards.spark

import org.apache.spark.sql.SparkSession

object SparkWithS3Example extends App {
  val session = SparkSession.builder().master("local").getOrCreate()

  // Set S3 configuration:
  session.sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", "http://localhost:4569")
  session.sparkContext.hadoopConfiguration.set("fs.s3n.endpoint", "http://localhost:4569")
  session.sparkContext.hadoopConfiguration.set("fs.s3.awsAccessKeyId", "anything")
  session.sparkContext.hadoopConfiguration.set("fs.s3.awsSecretAccessKey", "anything")

  import session.implicits._

  val PATH = "s3a://data/test"

  // Create test data
  val data = Seq("1","2","3").toDS()

  // Print data
  println("Test data:")
  data.show()

  // Write to S3
  println("Wring test data to S3")
  data.write.mode("overwrite").csv(PATH)

  // Read from S3
  println("Reading test data from S3")
  val data2 = session.read.csv(PATH)

  // show
  println("Test data read from S3:")
  data2.show()
}
