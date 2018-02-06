# AWS CLI with local Dynamo DB

How to connect from AWS CLI to local Dynamo DB.

## Prerequisite

### AWS Command Line Interface    

You need to install AWS CLI: https://aws.amazon.com/cli/

### Run Docker container

Start Docker container with local Dynamo DB:

    docker-compose up dynamodb

## Create table

Execute command in host system:

    aws dynamodb create-table --table-name myTable --attribute-definitions AttributeName=id,AttributeType=S --key-schema AttributeName=id,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 --endpoint-url http://localhost:8000

## List tables

Execute command in host system:

    aws dynamodb list-tables --endpoint-url http://localhost:8000 --output json
