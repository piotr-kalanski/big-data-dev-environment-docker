package com.datawizards.spark

import org.apache.spark.sql.SparkSession

object GenerateReport extends App {
  val session = SparkSession
    .builder()
    .enableHiveSupport()
    .config("spark.sql.warehouse.dir", "file:///data/warehouse") // important to setup directory for Hive metastore
    .master("local")
    .getOrCreate()

  import session.implicits._

  // Read data
  val people = session.read.table("people").as[Person]
  val experience = session.read.table("work_experience").as[WorkExperience]

  // Generate report
  val report = experience
    .joinWith(people, people("id") === experience("personId"))
    .groupByKey(x => x._1.jobTitle)
    .mapGroups{ case(jobTitle, values) => Report(jobTitle, values.size)}

  // Save report
  report.write.mode("overwrite").saveAsTable("hr_report")
}
