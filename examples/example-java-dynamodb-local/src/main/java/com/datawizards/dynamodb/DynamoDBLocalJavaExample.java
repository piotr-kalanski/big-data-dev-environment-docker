package com.datawizards.dynamodb;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DynamoDBLocalJavaExample {
    private static final String TEST_TABLE = "test_table";

    private final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
            // important to eetup endpoint for local Dynamo DB:
            new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
            .build();

    public static void main(String[] args) {
        DynamoDBLocalJavaExample example = new DynamoDBLocalJavaExample();
        example.createTable();
        example.listTables();
        example.addItem();
        example.getItem();
        example.deleteTable();
    }

    private void createTable() {
        System.out.println("=== Creating table");
        CreateTableRequest request = new CreateTableRequest()
                .withAttributeDefinitions(new AttributeDefinition(
                        "Name", ScalarAttributeType.S))
                .withKeySchema(new KeySchemaElement("Name", KeyType.HASH))
                .withProvisionedThroughput(new ProvisionedThroughput(
                        new Long(10), new Long(10)))
                .withTableName(TEST_TABLE);

        try {
            CreateTableResult result = ddb.createTable(request);
            System.out.println(result.getTableDescription().getTableName());
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    private void listTables() {
        System.out.println("=== Listing tables");
        ListTablesRequest request;

        boolean more_tables = true;
        String last_name = null;

        while(more_tables) {
            try {
                if (last_name == null) {
                    request = new ListTablesRequest().withLimit(10);
                }
                else {
                    request = new ListTablesRequest()
                            .withLimit(10)
                            .withExclusiveStartTableName(last_name);
                }

                ListTablesResult table_list = ddb.listTables(request);
                List<String> table_names = table_list.getTableNames();

                if (table_names.size() > 0) {
                    for (String cur_name : table_names) {
                        System.out.format("* %s\n", cur_name);
                    }
                } else {
                    System.out.println("No tables found!");
                    System.exit(0);
                }

                last_name = table_list.getLastEvaluatedTableName();
                if (last_name == null) {
                    more_tables = false;
                }

            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());
                System.exit(1);
            }
        }
        System.out.println("\nDone!");
    }

    private void addItem() {
        HashMap<String,AttributeValue> item_values =
                new HashMap<String,AttributeValue>();

        item_values.put("Name", new AttributeValue("name1"));

        System.out.println("=== Adding item");
        System.out.println(item_values);

        try {
            ddb.putItem(TEST_TABLE, item_values);
        } catch (ResourceNotFoundException e) {
            System.err.format("Error: The table \"%s\" can't be found.\n", TEST_TABLE);
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (AmazonServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }

    private void getItem() {
        HashMap<String,AttributeValue> key_to_get =
                new HashMap<String,AttributeValue>();

        key_to_get.put("Name", new AttributeValue("name1"));

        GetItemRequest request = new GetItemRequest()
                    .withKey(key_to_get)
                    .withTableName(TEST_TABLE);

        System.out.println("=== Reading item");

        try {
            Map<String,AttributeValue> returned_item =
                    ddb.getItem(request).getItem();
            System.out.println(returned_item);
            if (returned_item != null) {
                Set<String> keys = returned_item.keySet();
                for (String key : keys) {
                    System.out.format("%s: %s\n",
                            key, returned_item.get(key).toString());
                }
            } else {
                System.out.format("No item found with the key %s!\n", "name1");
            }
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    private void deleteTable() {
        System.out.println("=== Deleting table");
        try {
            ddb.deleteTable(TEST_TABLE);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }
}
