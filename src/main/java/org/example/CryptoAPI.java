package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CryptoAPI {
    private static final String API_KEY = "";

    public static JsonNode getCryptoData() throws Exception {
        String urlString = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?convert=USD";
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-CMC_PRO_API_KEY", API_KEY);
            connection.setRequestProperty("Accept", "application/json");

            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                throw new Exception("Failed to get data: HTTP " + statusCode);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(response.toString());
            }

        } catch (Exception e) {
            throw new Exception("Error reading API response", e);
        } finally {
            connection.disconnect();
        }
    }
}