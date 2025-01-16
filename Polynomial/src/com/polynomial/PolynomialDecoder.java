package com.polynomial;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PolynomialDecoder {

    public static void main(String[] args) {
    	String files[]= {
    			"C:\\Chikki\\Catlog\\input.json",
    			"C:\\Chikki\\Catlog\\input2.json"
    	};
    	for(int i=0;i<files.length;i++) {
    		String filePath=files[i];
    		try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(new File(filePath));
                int n = rootNode.get("keys").get("n").asInt();
                int k = rootNode.get("keys").get("k").asInt();
                System.out.println("Keys: n = " + n + ", k = " + k);
                List<Point> points = new ArrayList<>();
                Iterator<String> fieldNames = rootNode.fieldNames();
                while (fieldNames.hasNext()) {
                    String fieldName = fieldNames.next();
                    if (fieldName.equals("keys")) continue;
                    JsonNode fieldNode = rootNode.get(fieldName);
                    if (fieldNode.has("base") && fieldNode.has("value")) {
                        int base = fieldNode.get("base").asInt();
                        String value = fieldNode.get("value").asText();
                        BigInteger decodedValue = new BigInteger(value, base);
                        int x = Integer.parseInt(fieldName); 
                        points.add(new Point(x, decodedValue));
                    }
                }

            
                BigInteger secret = calculateSecretByLagrange(points);
                System.out.println("Secret by Lagrange Interpolation: " + secret);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    	}
            static class Point {
        int x;
        BigInteger y;

        Point(int x, BigInteger y) {
            this.x = x;
            this.y = y;
        }
    }

    private static BigInteger calculateSecretByLagrange(List<Point> points) {
        BigInteger secret = BigInteger.ZERO;

        for (int i = 0; i < points.size(); i++) {
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            for (int j = 0; j < points.size(); j++) {
                if (i != j) {
                    numerator = numerator.multiply(BigInteger.valueOf(-points.get(j).x));
                    denominator = denominator.multiply(BigInteger.valueOf(points.get(i).x - points.get(j).x)); 
                }
            }
            BigInteger term = points.get(i).y.multiply(numerator).divide(denominator);
            secret = secret.add(term);
        }

        return secret;
    }
}
