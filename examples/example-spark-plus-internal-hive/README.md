# Spark with internal Hive

How to work with Spark together with internal Hive.

## Prerequisites

Start Docker container with Hadoop cluster:

    docker-compose up namenode datanode1 resourcemanager nodemanager1 spark

## Run example

### Build application

    mvn package
    
### Copy jar

Copy jar to bind mount for spark jobs. By default it is: [spark-jobs/](../../spark-jobs)

### Run application

#### Generate test data

TODO - ten przyklad nie dziala poprawnie, ze wzgledu na brak integracji Spark z Hive

            docker container exec spark spark-submit --class com.datawizards.spark.GenerateTestData /opt/transformations/example-spark-plus-internal-hive-1.0-SNAPSHOT.jar
