# big-data-dev-environment-docker
Big Data development environment based on Docker.

![](images/environment_diagram.png)

# Run

Run environment with command:

    docker-compose up
    
You can run selected services by specifying names in the end, for example to run PostgreSQL and Elasticsearch run:

    docker-compose up postgres elasticsearch 
    
# Bind mounts

## Airflow dags

Airflow dags can be copied on host to directory [dags](/dags), so that they will be visible inside Docker container with airflow. 

## Spark applications

Spark applications can be copied to directory [spark-jobs](/spark-jobs), so that they will be visible inside Docker container with Spark at `/opt/transformations` directory.

## Kafka Connect JDBC drivers

If you want to add new JDBC drivers to Kafka Connect you can copy them locally to directory [kafka-connect-jdbc](/kafka-connect-jdbc).
