package com.polynomial;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class PolynomialDecoder {

    public static void main(String[] args) {
        // Path to the JSON file
        String filePath = "C:\\Chikki\\Catlog\\input.json";

        try {
            // Step 1: Read and parse JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(filePath));

            // Extract keys values
            int n = rootNode.get("keys").get("n").asInt();
            int k = rootNode.get("keys").get("k").asInt();
            System.out.println("Keys: n = " + n + ", k = " + k);

            // Step 2: Decode Y values
            Iterator<String> fieldNames = rootNode.fieldNames();
            int sum = 0; // To calculate the secret (c)
            System.out.println("Decoded Values:");
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();

                // Ignore "keys" field
                if (fieldName.equals("keys")) continue;

                JsonNode fieldNode = rootNode.get(fieldName);
                if (fieldNode.has("base") && fieldNode.has("value")) {
                    int base = fieldNode.get("base").asInt();
                    String value = fieldNode.get("value").asText();

                    // Decode the value from the given base to decimal
                    int decodedValue = Integer.parseInt(value, base);
                    System.out.println("Base: " + base + ", Encoded Value: " + value + ", Decoded Value: " + decodedValue);

                    // Accumulate for secret calculation
                    sum += decodedValue;
                }
            }

            // Step 3: Find the Secret (c)
            System.out.println("The secret (C): " + sum);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}