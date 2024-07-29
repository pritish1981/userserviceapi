package com.scaler.userservice.services;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
public class MultipartRequestSimulation {
    public static void main(String[] args) {
        try {
            // Create the file part
            File file = new File("C:\\Users\\Documents\\Pritish_Senapati_Resume.doc");
            FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);

            // Create the JSON payload part
            String jsonPayload = "{\"data\":\"Example Data\"}";
            StringBody jsonBody = new StringBody(jsonPayload, ContentType.APPLICATION_JSON);

            // Build the multipart request
            HttpEntity multipart = MultipartEntityBuilder.create()
                    .addPart("file", fileBody)
                    .addPart("payload", jsonBody)
                    .build();

            // Convert multipart entity to byte array (simulate sending the request)
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            multipart.writeTo(baos);
            byte[] multipartBytes = baos.toByteArray();

            // Simulate receiving the response by converting byte array back to string
            String multipartContent = new String(multipartBytes, StandardCharsets.UTF_8);
            System.out.println("Multipart Content (Simulated Request):");
            System.out.println(multipartContent);

            // Simulate server response (JSON content)
            String serverResponse = "{\"status\":\"success\",\"message\":\"File received\"}";
            System.out.println("\nServer Response (Simulated):");
            System.out.println(serverResponse);

            // Parse the simulated server response as JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseJson = mapper.readTree(new ByteArrayInputStream(serverResponse.getBytes(StandardCharsets.UTF_8)));

            // Process the JSON response
            String status = responseJson.get("status").asText();
            String message = responseJson.get("message").asText();

            System.out.println("\nParsed JSON Response:");
            System.out.println("Status: " + status);
            System.out.println("Message: " + message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

