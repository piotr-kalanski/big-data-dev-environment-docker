#!/bin/bash
psql --username "$POSTGRES_USER" -c "CREATE USER airflow WITH PASSWORD 'airflow';"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE airflow;
    GRANT ALL PRIVILEGES ON DATABASE airflow TO airflow;
EOSQL
