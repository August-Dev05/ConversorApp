package com.alura.conversor.modelo;

import java.util.Map;

public class ExchangeRateResponse {
    private String result; // "success" o "error"
    private String base_code; // Código de moneda base (ej: "USD")
    private Map<String, Double> conversion_rates; // Mapa de tasas de conversión

    public boolean isSuccess() {
        return "success".equals(result);
    }

    public String getBaseCode() {
        return base_code;
    }


    public Map<String, Double> getConversionRates() {
        return conversion_rates;
    }

    public Double getRate(String currency) {
        return conversion_rates != null ? conversion_rates.get(currency) : null;
    }
}