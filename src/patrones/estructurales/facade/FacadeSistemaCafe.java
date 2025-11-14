package patrones.estructurales.facade;

import models.Producto;
import patrones.comportamentales.estados.Orden;
import patrones.comportamentales.observer.Cocina;
import patrones.comportamentales.observer.Mesero;
import patrones.comportamentales.strategy.SeasonalMenuContext;
import patrones.comportamentales.strategy.SeasonalSpecialStrategy;
import patrones.creacionales.ProductoFactory;
import patrones.estructurales.decorator.ExtraCrema;
import patrones.estructurales.decorator.ExtraJarabe;
import patrones.estructurales.proxy.MenuService;
import patrones.estructurales.proxy.MenuServiceProxy;
import patrones.estructurales.services.HistoryService;
import patrones.estructurales.services.NotificationService;
import patrones.estructurales.services.OrderService;

import java.util.List;

public class FacadeSistemaCafe {

    // SERVICIOS ESPECIALIZADOS (Separación de Responsabilidades)
    private MenuService menuService;              // PROXY con caché
    private OrderService orderService;            // Gestión de órdenes
    private NotificationService notificationService; // Notificaciones
    private HistoryService historyService;        // Historial
    private SeasonalMenuContext seasonalContext;  // STRATEGY para menús especiales

    /**
     * Constructor con Inyección de Dependencias
     * Permite usar diferentes implementaciones de MenuService
     */
    public FacadeSistemaCafe() {
        // Inicializar servicios especializados
        this.menuService = new MenuServiceProxy(); // PROXY con caché interna
        this.orderService = new OrderService();
        this.notificationService = new NotificationService();
        this.historyService = new HistoryService();
        this.seasonalContext = new SeasonalMenuContext();

        // Registrar observadores en el sistema de notificaciones
        notificationService.registrarObservador(new Cocina("Principal"));
        notificationService.registrarObservador(new Mesero("Juan"));
        notificationService.registrarObservador(new Mesero("María"));

        System.out.println("✅ Facade inicializado con servicios especializados");
    }

    /* ============================================================
       MENÚ (Delegado a MenuService con PROXY)
    ============================================================ */

    /**
     * Muestra el menú regular (usando caché del Proxy)
     */
    public void mostrarMenu() {
        menuService.mostrarMenu();
    }

    /**
     * Invalida la caché del menú (útil cuando el menú cambia)
     */
    public void invalidarCacheMenu() {
        menuService.invalidarCache();
    }

    /* ============================================================
       MENÚS ESPECIALES POR TEMPORADA (STRATEGY)
    ============================================================ */

    /**
     * Configura la estrategia de menú especial para una temporada
     */
    public void configurarTemporada(SeasonalSpecialStrategy estrategia) {
        seasonalContext.setStrategy(estrategia);
    }

    /**
     * Muestra el menú especial de la temporada actual
     */
    public void mostrarMenuEspecial() {
        seasonalContext.mostrarMenuEspecial();
    }

    /**
     * Obtiene los platos especiales de la temporada
     */
    public List<Producto> obtenerPlatosEspeciales() {
        if (!seasonalContext.hayTemporadaActiva()) {
            System.out.println("ℹ️ No hay temporada especial activa");
            return List.of();
        }
        return seasonalContext.obtenerPlatosEspeciales();
    }

    /* ============================================================
       GESTIÓN DE ÓRDENES (Delegado a OrderService)
    ============================================================ */

    public void iniciarOrden(int mesa) {
        orderService.iniciarOrden(mesa);
    }

    public void agregarProducto(int mesa, String tipo, String nombre, double precio) {
        Producto producto = ProductoFactory.crearProducto(tipo, nombre, precio);
        orderService.agregarProductoAOrden(mesa, producto);
    }

    public void agregarObservaciones(int mesa, String obs) {
        orderService.agregarObservaciones(mesa, obs);
    }

    /* ============================================================
       DECORATOR — extras sobre el último producto agregado
    ============================================================ */

    private Producto obtenerUltimoProducto(int mesa) {
        Orden orden = orderService.obtenerOrdenTemporal(mesa).construir();
        List<Producto> productos = orden.getProductos();

        if (productos.isEmpty()) return null;

        return productos.get(productos.size() - 1);
    }

    public void agregarCremaExtra(int mesa) {
        Producto ultimo = obtenerUltimoProducto(mesa);
        if (ultimo == null) return;

        Orden orden = orderService.obtenerOrdenTemporal(mesa).construir();
        List<Producto> productos = orden.getProductos();

        productos.set(productos.size() - 1, new ExtraCrema(ultimo));
    }

    public void agregarJarabeExtra(int mesa, String sabor) {
        Producto ultimo = obtenerUltimoProducto(mesa);
        if (ultimo == null) return;

        Orden orden = orderService.obtenerOrdenTemporal(mesa).construir();
        List<Producto> productos = orden.getProductos();

        productos.set(productos.size() - 1, new ExtraJarabe(ultimo, sabor));
    }

    /* ============================================================
       PROCESAR ORDEN (Coordinación de servicios)
    ============================================================ */

    public void procesarOrden(int mesa) {
        Orden orden = orderService.finalizarOrden(mesa);

        System.out.println("\n🎯 Procesando orden #" + orden.getId());
        notificationService.notificar(orden, "NUEVA_ORDEN");

        System.out.println(orden);
    }

    /* ============================================================
       AVANZAR ESTADO (Delegado a OrderService + Coordinación)
    ============================================================ */

    public void avanzarOrden(int idOrden) {
        Orden orden = orderService.buscarOrdenActiva(idOrden);

        if (orden != null) {
            orden.avanzarEstado(); // STATE pattern

            if (orden.getEstado().equals("LISTA")) {
                notificationService.notificar(orden, "ORDEN_LISTA");
            }

            if (orden.getEstado().equals("ENTREGADA")) {
                orderService.removerOrdenActiva(orden);
                historyService.guardarOrden(orden);
                System.out.println("💾 Orden guardada en historial");
            }

        } else {
            System.out.println("❌ Orden no encontrada");
        }
    }

    /* ============================================================
       CONSULTAS (Delegadas a servicios especializados)
    ============================================================ */

    public void mostrarOrdenesActivas() {
        orderService.mostrarOrdenesActivas();
    }

    public void MementoHistorial() {
        historyService.mostrarHistorial();
    }
}
