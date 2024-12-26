package com.example.jtocr.routes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestSender {
    public String sendHttpRequest(String url, String method, String contentType, String requestBody) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();

        try {
            // Create the URL object
            URL requestUrl = new URL(url);

            // Open the connection
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod(method);

            // Set the content type if provided
            if (contentType != null) {
                connection.setRequestProperty("Content-Type", contentType);
            }

            // Send the request body if provided
            if (requestBody != null) {
                connection.setDoOutput(true);
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
                outputStream.close();
            }

            // Get the response
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            // Close resources
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response.toString();
    }
}
