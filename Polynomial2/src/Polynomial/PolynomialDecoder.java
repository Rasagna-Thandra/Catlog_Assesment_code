package Polynomial;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger; 
import java.util.Iterator;

public class PolynomialDecoder {

    public static void main(String[] args) {
        String filePath = "C:\\Chikki\\Catlog\\input.json";
        //String filePath = "C:\\Chikki\\Catlog\\input2.json";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(filePath));
            int n = rootNode.get("keys").get("n").asInt();
            int k = rootNode.get("keys").get("k").asInt();
            System.out.println("Keys: n = " + n + ", k = " + k);
            Iterator<String> fieldNames = rootNode.fieldNames();
            BigInteger sum = BigInteger.ZERO;
            System.out.println("Decoded Values:");
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                if (fieldName.equals("keys")) continue;
                JsonNode fieldNode = rootNode.get(fieldName);
                if (fieldNode.has("base") && fieldNode.has("value")) {
                    int base = fieldNode.get("base").asInt();
                    String value = fieldNode.get("value").asText();
                    BigInteger decodedValue = new BigInteger(value, base); 
                    System.out.println("Base: " + base + ", Encoded Value: " + value + ", Decoded Value: " + decodedValue);
                    sum = sum.add(decodedValue); 
                }
            }
            System.out.println("The secret (C): " + sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}