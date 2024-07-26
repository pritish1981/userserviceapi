package com.scaler.userservice.services;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MultipartRequestExample {
    public static void main(String[] args) throws IOException {
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

        // Print the multipart request to the console
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        multipart.writeTo(baos);
        String multipartContent = new String(baos.toByteArray(), StandardCharsets.UTF_8);

        System.out.println("Multipart Content:");
        System.out.println(multipartContent);
    }
}
/*
Explanation:::
File Part Creation:

File file = new File("path/to/your/document.docx");: Specifies the Word document file to be attached.
FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);: Creates a FileBody for the file.
XML Payload Part Creation:

String xmlPayload = "<payload><data>Example Data</data></payload>";: Specifies the XML payload.
StringBody xmlBody = new StringBody(xmlPayload, ContentType.APPLICATION_XML);: Creates a StringBody for the XML payload.
Multipart Request Building:

MultipartEntityBuilder.create(): Initializes a MultipartEntityBuilder.
addPart("file", fileBody): Adds the file part to the multipart request.
addPart("payload", xmlBody): Adds the XML payload part to the multipart request.
build(): Builds the multipart request.
 */
