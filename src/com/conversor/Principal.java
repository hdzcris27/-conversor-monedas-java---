package com.conversor;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n🌟 Bienvenido/a al Conversor de Monedas");
            System.out.println("Seleccione la moneda a la que desea convertir desde USD:");
            System.out.println("1 - Peso argentino (ARS)");
            System.out.println("2 - Boliviano (BOB)");
            System.out.println("3 - Real brasileño (BRL)");
            System.out.println("4 - Peso chileno (CLP)");
            System.out.println("5 - Peso colombiano (COP)");
            System.out.println("6 - Peso mexicano (MXN)");
            System.out.println("0 - Salir");
            System.out.print("👉 Elija una opción válida: ");
            opcion = teclado.nextInt();
            teclado.nextLine(); // Limpieza de buffer

            String monedaDestino = "";

            switch (opcion) {
                case 1 -> monedaDestino = "ARS";
                case 2 -> monedaDestino = "BOB";
                case 3 -> monedaDestino = "BRL";
                case 4 -> monedaDestino = "CLP";
                case 5 -> monedaDestino = "COP";
                case 6 -> monedaDestino = "MXN";
                case 0 -> System.out.println("👋 ¡Gracias por usar el conversor!");
                default -> System.out.println("❌ Opción inválida. Intente de nuevo.");
            }

            if (!monedaDestino.isEmpty()) {
                System.out.print("💵 Ingrese el monto en USD que desea convertir a " + monedaDestino + ": ");
                if (teclado.hasNextDouble()) {
                    double montoUSD = teclado.nextDouble();
                    teclado.nextLine(); // Limpieza de buffer

                    double tasa = APIHandler.obtenerTasaConJsonParser(monedaDestino);

                    if (tasa != -1) {
                        double convertido = montoUSD * tasa;
                        System.out.printf("✅ %.2f USD equivale a %.2f %s%n", montoUSD, convertido, monedaDestino);
                    } else {
                        System.out.println("❌ No se pudo obtener la tasa para " + monedaDestino);
                    }

                    System.out.println("\n🔍 Tasas seleccionadas frente al USD:");
                    APIHandler.mostrarMonedasFiltradas();

                } else {
                    System.out.println("❌ Entrada inválida. Por favor ingresa un número válido.");
                    teclado.nextLine(); // Limpia entrada incorrecta
                }
            }

        } while (opcion != 0);

        teclado.close();
    }
}