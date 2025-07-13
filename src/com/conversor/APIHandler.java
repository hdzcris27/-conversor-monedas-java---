package com.conversor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public class APIHandler {

    public static double obtenerTasaConJsonParser(String monedaDestino) {
        try {
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest solicitud = HttpRequest.newBuilder()
                    .uri(URI.create("https://v6.exchangerate-api.com/v6/d0444917994e15faedb57e74/latest/USD"))
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

            if (respuesta.statusCode() == 200) {
                String cuerpo = respuesta.body();
                JsonObject json = JsonParser.parseString(cuerpo).getAsJsonObject();
                JsonObject tasas = json.getAsJsonObject("conversion_rates");

                if (tasas.has(monedaDestino.toUpperCase())) {
                    double tasa = tasas.get(monedaDestino.toUpperCase()).getAsDouble();
                    return tasa;
                } else {
                    System.out.println("❌ Moneda no encontrada: " + monedaDestino.toUpperCase());
                    return -1;
                }

            } else {
                System.out.println("⚠️ Código HTTP inesperado: " + respuesta.statusCode());
                return -1;
            }

        } catch (Exception e) {
            System.out.println("⛔ Error al analizar JSON: " + e.getMessage());
            return -1;
        }
    }


    public static void mostrarMonedasFiltradas() {
        try {
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest solicitud = HttpRequest.newBuilder()
                    .uri(URI.create("https://v6.exchangerate-api.com/v6/d0444917994e15faedb57e74/latest/USD"))
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

            if (respuesta.statusCode() == 200) {
                String cuerpo = respuesta.body();
                JsonObject json = JsonParser.parseString(cuerpo).getAsJsonObject();
                JsonObject tasas = json.getAsJsonObject("conversion_rates");

                String[] monedasFiltradas = {"ARS", "BOB", "BRL", "CLP", "COP", "USD", "MXN"};

                System.out.println("🌍 Tasas frente al USD:");
                for (String codigo : monedasFiltradas) {
                    if (tasas.has(codigo)) {
                        double valor = tasas.get(codigo).getAsDouble();
                        System.out.printf("💰 %s → %.2f%n", codigo, valor);
                    }
                }
            } else {
                System.out.println("⚠️ Código HTTP inesperado: " + respuesta.statusCode());
            }

        } catch (Exception e) {
            System.out.println("⛔ Error filtrando monedas: " + e.getMessage());
        }
    }

    static class RespuestaAPI {
        Map<String, Double> conversion_rates;
    }
}