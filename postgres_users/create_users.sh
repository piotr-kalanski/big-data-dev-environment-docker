#!/bin/bash
psql --username postgres -c "CREATE USER airflow WITH PASSWORD 'airflow';"
psql -v ON_ERROR_STOP=1 --username postgres <<-EOSQL
    CREATE DATABASE airflow;
    GRANT ALL PRIVILEGES ON DATABASE airflow TO airflow;
EOSQL
