package app;

import java.util.Scanner;
import java.util.List;
import patrones.estructurales.facade.FacadeSistemaCafe;
import models.Producto;

public class Main {

    private static FacadeSistemaCafe facade;
    private static Scanner sc;

    public static void main(String[] args) {
        // Inicializar Facade (coordina todos los patrones)
        facade = new FacadeSistemaCafe();
        sc = new Scanner(System.in);

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> iniciarOrden();
                case 2 -> agregarProducto();
                case 3 -> agregarCrema();
                case 4 -> agregarJarabe();
                case 5 -> agregarObservaciones();
                case 6 -> finalizarOrden();
                case 7 -> avanzarOrden();
                case 8 -> facade.mostrarOrdenesActivas();
                case 9 -> facade.mostrarHistorial();
                case 10 -> facade.mostrarMenu();
                case 11 -> facade.activarMenuHalloween();
                case 12 -> facade.activarMenuNavidad();
                case 13 -> facade.desactivarMenuEspecial();
                case 14 -> facade.mostrarMenuEspecial();
                case 15 -> facade.invalidarCacheMenu();
                case 0 -> System.out.println("👋 Saliendo...");
                default -> System.out.println("❌ Opción inválida");
            }

        } while (opcion != 0);

        sc.close();
    }

    /* ============================================================
       MÉTODOS DE UI (integracion de patrones)
    ============================================================ */

    private static void mostrarMenu() {
        System.out.println("\n==============================================");
        System.out.println("     ☕ SISTEMA CAFETERÍA (REFACTORIZADO)");
        System.out.println("==============================================");
        System.out.println("1. Iniciar orden");
        System.out.println("2. Agregar producto");
        System.out.println("3. Agregar crema extra");
        System.out.println("4. Agregar jarabe extra");
        System.out.println("5. Agregar observaciones");
        System.out.println("6. Finalizar y procesar orden");
        System.out.println("7. Avanzar estado de orden");
        System.out.println("8. Ver órdenes activas");
        System.out.println("9. Ver historial");
        System.out.println("10. Ver menú regular");
        System.out.println("11. Activar menú de HALLOWEEN");
        System.out.println("12. Activar menú de NAVIDAD");
        System.out.println("13. Volver al menú REGULAR");
        System.out.println("14. Ver menú especial de temporada");
        System.out.println("15. Invalidar caché de menú");
        System.out.println("0. Salir");
    }

    private static void iniciarOrden() {
        int mesa = leerEntero("Número de mesa: ");
        facade.iniciarOrden(mesa);
    }

    private static void agregarProducto() {
        int mesa = leerEntero("Mesa: ");

        System.out.println("\nSeleccione categoría:");
        System.out.println("1. Bebidas");
        System.out.println("2. Comidas");
        System.out.println("3. Postres");
        int cat = leerEntero("Opción: ");

        String categoria = switch(cat) {
            case 1 -> "Bebidas";
            case 2 -> "Comidas";
            case 3 -> "Postres";
            default -> {
                System.out.println("❌ Categoría inválida");
                yield null;
            }
        };

        if (categoria == null) return;

        // Obtener productos del Facade (puede ser regular o especial)
        List<Producto> productos = facade.obtenerProductosPorCategoria(categoria);

        if (productos.isEmpty()) {
            System.out.println("❌ No hay productos disponibles en esta categoría");
            return;
        }

        System.out.println("\nSeleccione producto:");
        for (int i = 0; i < productos.size(); i++) {
            System.out.println((i + 1) + ". " + productos.get(i));
        }

        int opcion = leerEntero("Opción: ");

        if (opcion < 1 || opcion > productos.size()) {
            System.out.println("❌ Opción inválida");
            return;
        }

        Producto producto = productos.get(opcion - 1);
        facade.agregarProducto(mesa, producto.getCategoria(),
                producto.getNombre(), producto.getPrecio());
    }

    private static void agregarCrema() {
        int mesa = leerEntero("Mesa: ");
        facade.agregarCremaExtra(mesa);
    }

    private static void agregarJarabe() {
        int mesa = leerEntero("Mesa: ");

        System.out.println("\nSeleccione sabor de jarabe:");
        System.out.println("1. Vainilla");
        System.out.println("2. Caramelo");
        System.out.println("3. Avellana");
        int sabor = leerEntero("Opción: ");

        String saborTxt = switch(sabor) {
            case 1 -> "Vainilla";
            case 2 -> "Caramelo";
            case 3 -> "Avellana";
            default -> {
                System.out.println("❌ Sabor inválido");
                yield null;
            }
        };

        if (saborTxt != null) {
            facade.agregarJarabeExtra(mesa, saborTxt);
        }
    }

    private static void agregarObservaciones() {
        int mesa = leerEntero("Mesa: ");
        sc.nextLine(); // Limpiar buffer
        System.out.print("Observación: ");
        String obs = sc.nextLine();
        facade.agregarObservaciones(mesa, obs);
    }

    private static void finalizarOrden() {
        int mesa = leerEntero("Mesa: ");
        facade.procesarOrden(mesa);
    }

    private static void avanzarOrden() {
        int id = leerEntero("ID de la orden: ");
        facade.avanzarOrden(id);
    }

    // Método auxiliar para leer enteros de forma segura
    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor ingrese un número válido");
            }
        }
    }
}