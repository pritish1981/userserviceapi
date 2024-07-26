package com.scaler.userservice.services;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

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

            // Create the XML payload part
            String xmlPayload = "<payload><data>Example Data</data></payload>";
            StringBody xmlBody = new StringBody(xmlPayload, ContentType.APPLICATION_XML);

            // Build the multipart request
            HttpEntity multipart = MultipartEntityBuilder.create()
                    .addPart("file", fileBody)
                    .addPart("payload", xmlBody)
                    .build();

            // Convert multipart entity to byte array (simulate sending the request)
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            multipart.writeTo(baos);
            byte[] multipartBytes = baos.toByteArray();

            // Simulate receiving the response by converting byte array back to string
            String multipartContent = new String(multipartBytes, StandardCharsets.UTF_8);
            System.out.println("Multipart Content (Simulated Request):");
            System.out.println(multipartContent);

            // Simulate server response (XML content)
            String serverResponse = "<response><status>success</status><message>File received</message></response>";
            System.out.println("\nServer Response (Simulated):");
            System.out.println(serverResponse);

            // Parse the simulated server response as XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(serverResponse.getBytes(StandardCharsets.UTF_8)));

            // Process the XML response
            String status = document.getElementsByTagName("status").item(0).getTextContent();
            String message = document.getElementsByTagName("message").item(0).getTextContent();

            System.out.println("\nParsed XML Response:");
            System.out.println("Status: " + status);
            System.out.println("Message: " + message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

