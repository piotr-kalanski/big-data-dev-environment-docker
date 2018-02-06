# Spark job example - read/write from/to HDFS

Example how to integrate Spark with local Hadoop.

## Prerequisites

Start Docker container with Hadoop cluster:

    docker-compose up namenode datanode1 resourcemanager nodemanager1 spark

## Run example

### Build application

    mvn package
    
### Copy jar

Copy jar to bind mount for spark jobs. By default it is: [spark-jobs/](../../spark-jobs)

To avoid copying jar for convenience you can change bind mount for Spark container to `target` directory in [docker-compose.yml](../../docker-compose.yml) file. Example:

    spark:
        [...]
        volumes:
            - ./target:/opt/transformations

### Run application

#### Option A - execute from host system

Run jar in Docker container with Spark:

    docker container exec -it spark spark-submit --class com.datawizards.spark.SparkWithHDFSExample --master yarn /opt/transformations/example-spark-hdfs-1.0-SNAPSHOT.jar

#### Option B - execute from Docker container

another option is to login to Docker container with Spark:

    docker container exec -it spark bash
    
and then run spark-submit:

    spark-submit --class com.datawizards.spark.SparkWithHDFSExample --master yarn /opt/transformations/example-spark-hdfs-1.0-SNAPSHOT.jar
    
### Verify that data is saved

List files in HDFS directory from host system:

    docker container exec -it namenode hadoop fs -ls /data/test