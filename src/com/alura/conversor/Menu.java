package com.alura.conversor;

import com.alura.conversor.api.ApiClient;
import com.alura.conversor.modelo.ExchangeRateResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private final ApiClient apiClient;
    private final Scanner scanner;

    public Menu() {
        this.apiClient = new ApiClient();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        String menu = """
        **************************************************
        Bienvenido al Conversor de Monedas :)
        
        1). Dolar (USD) => Peso Argentino (ARS)
        2). Peso Argentino (ARS) => Dolar (USD)
        3). Dolar (USD) => Real Brasileño (BRL)
        4). Real Brasileño (BRL) => Dolar (USD)
        5). Dolar (USD) => Peso Colombiano (COP)
        6). Peso Colombiano (COP) => Dolar (USD)
        7). Salir
        ***************************************************
        """;

        while (true) {
            System.out.println(menu);
            System.out.print("Elija una opción: ");
            int opcion = scanner.nextInt();

            if (opcion == 7) {
                System.out.println("¡Hasta luego!");
                break;
            }

            try {
                System.out.print("Ingrese el monto a convertir: ");
                double monto = scanner.nextDouble();
                String resultado = convertirMoneda(opcion, monto);
                System.out.println(resultado);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private String convertirMoneda(int opcion, double monto) {
        try {
            ExchangeRateResponse response = apiClient.getExchangeRates("USD");
            Map<String, Double> tasas = response.getConversionRates();

            return switch (opcion) {
                case 1 -> formatResultado(monto, "USD", "ARS", monto * tasas.get("ARS"));
                case 2 -> formatResultado(monto, "ARS", "USD", monto / tasas.get("ARS"));
                case 3 -> formatResultado(monto, "USD", "BRL", monto * tasas.get("BRL"));
                case 4 -> formatResultado(monto, "BRL", "USD", monto / tasas.get("BRL"));
                case 5 -> formatResultado(monto, "USD", "COP", monto * tasas.get("COP"));
                case 6 -> formatResultado(monto, "COP", "USD", monto / tasas.get("COP"));
                default -> "Opción inválida. Intente nuevamente.";
            };
        } catch (IOException e) {
            return "Error al conectar con la API: " + e.getMessage();
        }
    }

    private String formatResultado(double monto, String from, String to, double resultado) {
        return String.format("%,.2f %s = %,.2f %s", monto, from, resultado, to);
    }
}