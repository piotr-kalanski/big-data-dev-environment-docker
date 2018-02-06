package com.datawizards.spark

import org.apache.spark.sql.SparkSession

object SparkWithHDFSExample extends App {
  val session = SparkSession.builder().master("local").getOrCreate()
  import session.implicits._

  val PATH = "/data/test"

  // Create test data
  val data = Seq("1","2","3").toDS()

  // Print data
  println("Test data:")
  data.show()

  // Write to HDFS
  println("Wring test data to HDFS")
  data.write.mode("overwrite").csv(PATH)

  // Read from HDFS
  println("Reading test data from HDFS")
  val data2 = session.read.csv(PATH)

  // show
  println("Test data read from HDFS:")
  data2.show()
}
