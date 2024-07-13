package com.scaler.userservice.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class WordToBase64Converter {
    public static String convertWordToBase64(String filePath) {
        String base64EncodedString = null;
        try (FileInputStream fileInputStream = new FileInputStream(new File(filePath));
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            byte[] fileBytes = byteArrayOutputStream.toByteArray();
            base64EncodedString = Base64.getEncoder().encodeToString(fileBytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64EncodedString;
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Documents\\Pritish_Senapati_Resume.doc"; // Provide the path to your Word document
        String base64String = convertWordToBase64(filePath);
        System.out.println("Base64 Encoded String: ");
        System.out.println(base64String);
    }

    /*
         <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>5.2.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-collections4</artifactId>
            <version>4.4</version>
        </dependency>

    */


}
