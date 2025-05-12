package com.alura.conversor.servicios;


import com.alura.conversor.api.ApiClient;
import com.alura.conversor.modelo.ExchangeRateResponse;

public class CurrencyService {
    private final ApiClient apiClient;

    public CurrencyService() {
        this.apiClient = new ApiClient();
    }

    public String convertir(String fromCurrency, String toCurrency, double amount) {
        try {
            ExchangeRateResponse response = apiClient.getExchangeRates(fromCurrency);
            double rate = response.getConversionRates().get(toCurrency);
            double resultado = amount * rate;
            return String.format("%,.2f %s = %,.2f %s", amount, fromCurrency, resultado, toCurrency);
        } catch (Exception e) {
            return "Error al convertir: " + e.getMessage();
        }
    }
}