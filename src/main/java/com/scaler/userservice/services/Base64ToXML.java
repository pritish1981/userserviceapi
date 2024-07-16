package com.scaler.userservice.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Base64ToXML {
    public static String convertFileToBase64(String filePath) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(new File(filePath));
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            byte[] fileBytes = byteArrayOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(fileBytes);
        }
    }

    public static void addBase64ToXML(String base64String, String outputPath) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        // Create root element
        Element root = document.createElement("root");
        document.appendChild(root);

        // Create base64 element
        Element base64Element = document.createElement("base64Data");
        base64Element.appendChild(document.createTextNode(base64String));
        root.appendChild(base64Element);

        // Transform the DOM Object to an XML File
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(outputPath));

        transformer.transform(domSource, streamResult);
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Documents\\Pritish_Senapati_Resume.doc"; // Path to your .docx file
        String outputPath = "C:\\Users\\Documents\\output.xml";// Path to output XML file

        try {
            String base64String = convertFileToBase64(filePath);
            addBase64ToXML(base64String, outputPath);
            System.out.println("Base64 encoded string added to XML and saved to " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
