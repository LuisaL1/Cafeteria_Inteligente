package app;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import patrones.estructurales.facade.FacadeSistemaCafe;
import patrones.comportamentales.strategy.HalloweenSpecialStrategy;
import patrones.comportamentales.strategy.ChristmasSpecialStrategy;
import models.Producto;

public class Main {

    // Arrays dinámicos para productos (pueden cambiar según la temporada)
    private static List<String> bebidasNombres = new ArrayList<>();
    private static List<Double> bebidasPrecios = new ArrayList<>();

    private static List<String> comidasNombres = new ArrayList<>();
    private static List<Double> comidasPrecios = new ArrayList<>();

    private static List<String> postresNombres = new ArrayList<>();
    private static List<Double> postresPrecios = new ArrayList<>();

    public static void main(String[] args) {

        // Inicializar menú regular
        cargarMenuRegular();

        // Usar el Facade REFACTORIZADO (con Proxy y Strategy)
        FacadeSistemaCafe facade = new FacadeSistemaCafe();
        Scanner sc = new Scanner(System.in);

        int opcion;

        do {
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
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {

                case 1 -> {
                    System.out.print("Número de mesa: ");
                    int mesa = sc.nextInt();
                    facade.iniciarOrden(mesa);
                    System.out.println("✔ Orden iniciada para mesa " + mesa);
                }

                case 2 -> {
                    System.out.print("Mesa: ");
                    int mesa = sc.nextInt();

                    int categoria = seleccionarCategoria(sc);

                    if (categoria == 1) seleccionarProducto(sc, facade, mesa, "bebida", bebidasNombres, bebidasPrecios);
                    if (categoria == 2) seleccionarProducto(sc, facade, mesa, "comida", comidasNombres, comidasPrecios);
                    if (categoria == 3) seleccionarProducto(sc, facade, mesa, "postre", postresNombres, postresPrecios);
                }

                case 3 -> {
                    System.out.print("Mesa: ");
                    int mesa = sc.nextInt();
                    facade.agregarCremaExtra(mesa);
                    System.out.println("✔ Crema extra agregada");
                }

                case 4 -> {
                    System.out.print("Mesa: ");
                    int mesa = sc.nextInt();
                    int sabor = seleccionarSaborJarabe(sc);
                    String saborTxt = (sabor == 1) ? "Vainilla" :
                            (sabor == 2) ? "Caramelo" :
                                    "Avellana";

                    facade.agregarJarabeExtra(mesa, saborTxt);
                    System.out.println("✔ Jarabe extra agregado");
                }

                case 5 -> {
                    System.out.print("Mesa: ");
                    int mesa = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Observación: ");
                    String obs = sc.nextLine();
                    facade.agregarObservaciones(mesa, obs);
                }

                case 6 -> {
                    System.out.print("Mesa: ");
                    int mesa = sc.nextInt();
                    facade.procesarOrden(mesa);
                }

                case 7 -> {
                    System.out.print("ID de la orden: ");
                    int id = sc.nextInt();
                    facade.avanzarOrden(id);
                }

                case 8 -> facade.mostrarOrdenesActivas();

                case 9 -> facade.MementoHistorial();

                // ===== NUEVAS FUNCIONALIDADES =====

                case 10 -> {
                    System.out.println("\n📋 Mostrando menú regular (con caché Proxy):");
                    facade.mostrarMenu();
                }

                case 11 -> {
                    System.out.println("\n🎃 Activando menú especial de HALLOWEEN...");
                    HalloweenSpecialStrategy halloween = new HalloweenSpecialStrategy();
                    facade.configurarTemporada(halloween);
                    cargarMenuEspecial(halloween.obtenerPlatosEspeciales());
                    System.out.println("✅ Menú de Halloween activado. Los productos están disponibles en 'Agregar producto'.");
                }

                case 12 -> {
                    System.out.println("\n🎄 Activando menú especial de NAVIDAD...");
                    ChristmasSpecialStrategy navidad = new ChristmasSpecialStrategy();
                    facade.configurarTemporada(navidad);
                    cargarMenuEspecial(navidad.obtenerPlatosEspeciales());
                    System.out.println("✅ Menú de Navidad activado. Los productos están disponibles en 'Agregar producto'.");
                }

                case 13 -> {
                    System.out.println("\n🔄 Volviendo al menú regular...");
                    cargarMenuRegular();
                    facade.configurarTemporada(null); // Desactiva estrategia
                    System.out.println("✅ Menú regular restaurado.");
                }

                case 14 -> {
                    System.out.println("\n🎉 Mostrando menú especial de temporada:");
                    facade.mostrarMenuEspecial();
                }

                case 15 -> {
                    System.out.println("\n🔄 Invalidando caché de menú...");
                    facade.invalidarCacheMenu();
                    System.out.println("✅ Caché invalidada. Próxima consulta recargará desde el origen.");
                }

                case 0 -> System.out.println("👋 Saliendo...");

                default -> System.out.println("❌ Opción inválida");
            }

        } while (opcion != 0);

        sc.close();
    }

    // =============================================================
    // GESTIÓN DINÁMICA DE MENÚ
    // =============================================================

    /**
     * Carga el menú regular de la cafetería
     */
    private static void cargarMenuRegular() {
        // Limpiar arrays
        bebidasNombres.clear();
        bebidasPrecios.clear();
        comidasNombres.clear();
        comidasPrecios.clear();
        postresNombres.clear();
        postresPrecios.clear();

        // Bebidas regulares
        bebidasNombres.add("Café Americano");
        bebidasPrecios.add(5000.0);
        bebidasNombres.add("Capuchino");
        bebidasPrecios.add(6500.0);
        bebidasNombres.add("Latte");
        bebidasPrecios.add(7000.0);
        bebidasNombres.add("Mocca");
        bebidasPrecios.add(7500.0);

        // Comidas regulares
        comidasNombres.add("Sandwich");
        comidasPrecios.add(8500.0);
        comidasNombres.add("Croissant");
        comidasPrecios.add(7000.0);
        comidasNombres.add("Panini");
        comidasPrecios.add(9000.0);

        // Postres regulares
        postresNombres.add("Brownie");
        postresPrecios.add(5500.0);
        postresNombres.add("Cheesecake");
        postresPrecios.add(6500.0);
        postresNombres.add("Galleta");
        postresPrecios.add(3000.0);
    }

    /**
     * Carga el menú especial de temporada (reemplaza el menú regular)
     */
    private static void cargarMenuEspecial(List<Producto> productosEspeciales) {
        // Limpiar arrays
        bebidasNombres.clear();
        bebidasPrecios.clear();
        comidasNombres.clear();
        comidasPrecios.clear();
        postresNombres.clear();
        postresPrecios.clear();

        // Clasificar productos especiales por categoría
        for (Producto producto : productosEspeciales) {
            String categoria = producto.getCategoria();
            String nombre = producto.getNombre();
            double precio = producto.getPrecio();

            switch (categoria) {
                case "Bebida" -> {
                    bebidasNombres.add(nombre);
                    bebidasPrecios.add(precio);
                }
                case "Comida" -> {
                    comidasNombres.add(nombre);
                    comidasPrecios.add(precio);
                }
                case "Postre" -> {
                    postresNombres.add(nombre);
                    postresPrecios.add(precio);
                }
            }
        }
    }

    // =============================================================
    // MÉTODOS DE SELECCIÓN NUMÉRICA
    // =============================================================

    private static int seleccionarCategoria(Scanner sc) {
        System.out.println("\nSeleccione categoría:");
        System.out.println("1. Bebidas");
        System.out.println("2. Comidas");
        System.out.println("3. Postres");
        System.out.print("Opción: ");
        return sc.nextInt();
    }

    private static void seleccionarProducto(
            Scanner sc,
            FacadeSistemaCafe facade,
            int mesa,
            String tipo,
            List<String> nombres,
            List<Double> precios) {

        if (nombres.isEmpty()) {
            System.out.println("❌ No hay productos disponibles en esta categoría.");
            return;
        }

        System.out.println("\nSeleccione producto:");

        for (int i = 0; i < nombres.size(); i++) {
            System.out.println((i + 1) + ". " + nombres.get(i) + " - $" + precios.get(i));
        }

        System.out.print("Opción: ");
        int op = sc.nextInt();

        if (op < 1 || op > nombres.size()) {
            System.out.println("❌ Opción inválida");
            return;
        }

        int i = op - 1;
        facade.agregarProducto(mesa, tipo, nombres.get(i), precios.get(i));
        System.out.println("✔ Producto agregado");
    }

    private static int seleccionarSaborJarabe(Scanner sc) {
        System.out.println("\nSeleccione sabor de jarabe:");
        System.out.println("1. Vainilla");
        System.out.println("2. Caramelo");
        System.out.println("3. Avellana");
        System.out.print("Opción: ");
        return sc.nextInt();
    }
}
