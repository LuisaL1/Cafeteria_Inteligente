package patrones.estructurales.facade;

import models.Producto;
import patrones.comportamentales.estados.Orden;
import patrones.comportamentales.observer.Cocina;
import patrones.comportamentales.observer.Mesero;
import patrones.comportamentales.strategy.seasonalmenuContext.*;
import patrones.creacionales.ProductoFactory;
import patrones.estructurales.decorator.ExtraCrema;
import patrones.estructurales.decorator.ExtraJarabe;
import patrones.estructurales.proxy.MenuService;
import patrones.estructurales.proxy.MenuServiceProxy;
import patrones.estructurales.services.HistoryService;
import patrones.estructurales.services.NotificationService;
import patrones.estructurales.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;


public class FacadeSistemaCafe {

    // ========== SERVICIOS ESPECIALIZADOS ==========
    private MenuService menuService;              // PROXY con caché
    private OrderService orderService;            // Gestión de órdenes (BUILDER implícito)
    private NotificationService notificationService; // OBSERVER
    private HistoryService historyService;        // MEMENTO
    private SeasonalMenuContext seasonalContext;  // STRATEGY para menús especiales

    /**
     * Constructor - Inicializa todos los servicios y patrones
     */
    public FacadeSistemaCafe() {
        // Inicializar servicios
        this.menuService = new MenuServiceProxy(); // PROXY
        this.orderService = new OrderService();    // BUILDER (interno)
        this.notificationService = new NotificationService(); // OBSERVER
        this.historyService = new HistoryService(); // MEMENTO
        this.seasonalContext = new SeasonalMenuContext(); // STRATEGY

        // Registrar observadores (OBSERVER)
        notificationService.registrarObservador(new Cocina("Principal"));
        notificationService.registrarObservador(new Mesero("Juan"));
        notificationService.registrarObservador(new Mesero("María"));

        System.out.println("✅ Facade inicializado con servicios especializados");
    }

    /* ============================================================
       MENÚ REGULAR (PROXY + SINGLETON)
    ============================================================ */

    /**
     * Muestra el menú regular (usando caché del Proxy)
     */
    public void mostrarMenu() {
        System.out.println("\n📋 Mostrando menú regular (con caché Proxy):");
        menuService.mostrarMenu();
    }

    /**
     * Invalida la caché del menú
     */
    public void invalidarCacheMenu() {
        System.out.println("\n🔄 Invalidando caché de menú...");
        menuService.invalidarCache();
        System.out.println("✅ Caché invalidada. Próxima consulta recargará desde el origen.");
    }

    /**
     * Obtiene productos de una categoría específica
     */
    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        // Si hay menú especial activo, devolver productos especiales
        if (seasonalContext.hayTemporadaActiva()) {
            return seasonalContext.obtenerPlatosEspeciales()
                    .stream()
                    .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                    .collect(Collectors.toList());
        }

        // Si no, devolver del menú regular
        return switch(categoria.toLowerCase()) {
            case "bebidas", "bebida" -> menuService.obtenerBebidas();
            case "comidas", "comida" -> menuService.obtenerComidas();
            case "postres", "postre" -> menuService.obtenerPostres();
            default -> List.of();
        };
    }

    /* ============================================================
       MENÚS ESPECIALES POR TEMPORADA (STRATEGY)
    ============================================================ */

    /**
     * Activa el menú especial de HALLOWEEN
     */
    public void activarMenuHalloween() {
        System.out.println("\n🎃 Activando menú especial de HALLOWEEN...");
        HalloweenSpecialStrategy halloween = new HalloweenSpecialStrategy();
        seasonalContext.setStrategy(halloween);
        System.out.println("✅ Menú de Halloween activado. Los productos están disponibles en 'Agregar producto'.");
    }

    /**
     * Activa el menú especial de NAVIDAD
     */
    public void activarMenuNavidad() {
        System.out.println("\n🎄 Activando menú especial de NAVIDAD...");
        ChristmasSpecialStrategy navidad = new ChristmasSpecialStrategy();
        seasonalContext.setStrategy(navidad);
        System.out.println("✅ Menú de Navidad activado. Los productos están disponibles en 'Agregar producto'.");
    }

    /**
     * Desactiva el menú especial y vuelve al regular
     */
    public void desactivarMenuEspecial() {
        System.out.println("\n🔄 Volviendo al menú regular...");
        seasonalContext.setStrategy(null);
        System.out.println("✅ Menú regular restaurado.");
    }

    /**
     * Muestra el menú especial de la temporada actual
     */
    public void mostrarMenuEspecial() {
        System.out.println("\n🎉 Mostrando menú especial de temporada:");
        seasonalContext.mostrarMenuEspecial();
    }

    /**
     * Verifica si hay un menú especial activo
     */
    public boolean hayMenuEspecialActivo() {
        return seasonalContext.hayTemporadaActiva();
    }

    /* ============================================================
       GESTIÓN DE ÓRDENES (BUILDER + STATE)
    ============================================================ */

    /**
     * Inicia una nueva orden para una mesa (BUILDER)
     */
    public void iniciarOrden(int mesa) {
        orderService.iniciarOrden(mesa);
        System.out.println("✅ Orden iniciada para mesa " + mesa);
    }

    /**
     * Agrega un producto a la orden (FACTORY + BUILDER)
     */
    public void agregarProducto(int mesa, String tipo, String nombre, double precio) {
        Producto producto = ProductoFactory.crearProducto(tipo, nombre, precio); // FACTORY
        orderService.agregarProductoAOrden(mesa, producto); // BUILDER
        System.out.println("✅ Producto agregado: " + nombre);
    }

    /**
     * Agrega observaciones a la orden (BUILDER)
     */
    public void agregarObservaciones(int mesa, String obs) {
        orderService.agregarObservaciones(mesa, obs);
        System.out.println("✅ Observaciones agregadas");
    }

    /* ============================================================
       DECORATOR - Extras sobre productos
    ============================================================ */

    /**
     * Agrega crema extra al último producto agregado (DECORATOR)
     */
    public void agregarCremaExtra(int mesa) {
        Producto ultimo = obtenerUltimoProducto(mesa);
        if (ultimo == null) {
            System.out.println("❌ No hay productos en la orden");
            return;
        }

        Orden orden = orderService.obtenerOrdenTemporal(mesa).construir();
        List<Producto> productos = orden.getProductos();
        productos.set(productos.size() - 1, new ExtraCrema(ultimo)); // DECORATOR
        System.out.println("✅ Crema extra agregada (+$0.50)");
    }

    /**
     * Agrega jarabe extra al último producto agregado (DECORATOR)
     */
    public void agregarJarabeExtra(int mesa, String sabor) {
        Producto ultimo = obtenerUltimoProducto(mesa);
        if (ultimo == null) {
            System.out.println("❌ No hay productos en la orden");
            return;
        }

        Orden orden = orderService.obtenerOrdenTemporal(mesa).construir();
        List<Producto> productos = orden.getProductos();
        productos.set(productos.size() - 1, new ExtraJarabe(ultimo, sabor)); // DECORATOR
        System.out.println("✅ Jarabe de " + sabor + " agregado (+$0.75)");
    }

    private Producto obtenerUltimoProducto(int mesa) {
        Orden orden = orderService.obtenerOrdenTemporal(mesa).construir();
        List<Producto> productos = orden.getProductos();
        return productos.isEmpty() ? null : productos.get(productos.size() - 1);
    }

    /* ============================================================
       PROCESAR Y AVANZAR ÓRDENES (STATE + OBSERVER + MEMENTO)
    ============================================================ */

    /**
     * Finaliza y procesa la orden (BUILDER + OBSERVER)
     */
    public void procesarOrden(int mesa) {
        Orden orden = orderService.finalizarOrden(mesa); // BUILDER

        System.out.println("\n🎯 Procesando orden #" + orden.getId());
        notificationService.notificar(orden, "NUEVA_ORDEN"); // OBSERVER

        System.out.println(orden);
        System.out.println("✅ Orden procesada exitosamente");
    }

    /**
     * Avanza el estado de una orden (STATE + OBSERVER + MEMENTO)
     */
    public void avanzarOrden(int idOrden) {
        Orden orden = orderService.buscarOrdenActiva(idOrden);

        if (orden != null) {
            orden.avanzarEstado(); // STATE

            if (orden.getEstado().equals("LISTA")) {
                notificationService.notificar(orden, "ORDEN_LISTA"); // OBSERVER
            }

            if (orden.getEstado().equals("ENTREGADA")) {
                orderService.removerOrdenActiva(orden);
                historyService.guardarOrden(orden); // MEMENTO
                System.out.println("💾 Orden guardada en historial");
            }

        } else {
            System.out.println("❌ Orden no encontrada");
        }
    }

    /* ============================================================
       CONSULTAS
    ============================================================ */

    /**
     * Muestra todas las órdenes activas
     */
    public void mostrarOrdenesActivas() {
        orderService.mostrarOrdenesActivas();
    }

    /**
     * Muestra el historial completo de órdenes (MEMENTO)
     */
    public void mostrarHistorial() {
        historyService.mostrarHistorial();
    }
}