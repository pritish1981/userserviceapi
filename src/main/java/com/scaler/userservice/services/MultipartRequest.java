package com.scaler.userservice.services;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.File;
import java.io.IOException;
public class MultipartRequest {
    public static void main(String[] args) {
        // Create the HTTP client
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            // Create the POST request
            HttpPost uploadFile = new HttpPost("http://yourserver/upload");

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

            // Set the entity to the request
            uploadFile.setEntity(multipart);

            // Execute the request
            CloseableHttpResponse response = httpClient.execute(uploadFile);
            try {
                System.out.println(response.getStatusLine());
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    System.out.println("Response content length: " + responseEntity.getContentLength());
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
/*
Explanation:
HttpClient Setup:

CloseableHttpClient httpClient = HttpClients.createDefault();: Creates an instance of CloseableHttpClient.
POST Request Creation:

HttpPost uploadFile = new HttpPost("http://yourserver/upload");: Creates a POST request to the specified URL.
File and XML Payload Creation:

File file = new File("path/to/your/document.doc");: Specifies the file to be uploaded.
FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);: Creates a FileBody for the file.
String xmlPayload = "<payload><data>Example Data</data></payload>";: Specifies the XML payload.
StringBody xmlBody = new StringBody(xmlPayload, ContentType.APPLICATION_XML);: Creates a StringBody for the XML payload.
Multipart Request Building:

MultipartEntityBuilder.create(): Initializes a MultipartEntityBuilder.
addPart("file", fileBody): Adds the file part.
addPart("payload", xmlBody): Adds the XML payload part.
build(): Builds the multipart request.
Executing the Request:

uploadFile.setEntity(multipart);: Sets the multipart entity to the POST request.
CloseableHttpResponse response = httpClient.execute(uploadFile);: Executes the POST request.
The response is then handled to print the status line and content length.
Maven Dependency:
<dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.13</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpmime -->
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpmime</artifactId>
            <version>4.5.13</version>
        </dependency>
 */
