package com.datawizards.spark

import org.apache.spark.sql.SparkSession

object GenerateTestData extends App {
  val session = SparkSession
    .builder()
    /*

    aktualnie to nie działa na klastrze, ponieważ do Hive support potrzebne są dodatkowe liby. Próbowałem dodać zależności w maven shade plugin, ale jest ich za mało
    bez tej opcji za kazdym tworzony jest Hive metastore

     */
    .enableHiveSupport()
    .config("spark.sql.warehouse.dir", "file:///data/warehouse") // important to setup directory for Hive metastore
    .master("local")
    .getOrCreate()

  import session.implicits._

  val people = Seq(
    Person(1, "Piotr", "Mr"),
    Person(2, "Anna", "dr"),
    Person(3, "Rudolf", "?")
  ).toDS()

  val experience = Seq(
    WorkExperience(1, "Team Leader", "StepStone"),
    WorkExperience(2, "UX", "Coca Cola"),
    WorkExperience(3, "Developer", "Coca Cola")
  ).toDS()


  people.write.mode("overwrite").saveAsTable("people")
  experience.write.mode("overwrite").saveAsTable("work_experience")
}
