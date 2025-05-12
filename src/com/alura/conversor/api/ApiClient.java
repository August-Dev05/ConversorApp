package com.alura.conversor.api;

import com.alura.conversor.modelo.ExchangeRateResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiClient {
    private static final String API_KEY = "d1d1cd6b70bfead3364978e0";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";
    private static final int TIMEOUT_MS = 5000;

    public ExchangeRateResponse getExchangeRates(String baseCurrency) throws IOException {
        HttpURLConnection connection = null;
        try {
            // 1. Configurar conexión
            URL url = new URL(BASE_URL + baseCurrency);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TIMEOUT_MS);
            connection.setReadTimeout(TIMEOUT_MS);

            // 2. Validar respuesta HTTP
            int status = connection.getResponseCode();
            if (status != 200) {
                throw new IOException("Error HTTP " + status + ": " + connection.getResponseMessage());
            }

            // 3. Parsear JSON con Gson
            try (InputStreamReader reader = new InputStreamReader(
                    connection.getInputStream(), StandardCharsets.UTF_8)) {
                return new Gson().fromJson(reader, ExchangeRateResponse.class);
            }

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    protected String getApiUrl() {
        return BASE_URL;
    }
}