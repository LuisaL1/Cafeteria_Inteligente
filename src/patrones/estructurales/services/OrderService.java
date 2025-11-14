package patrones.estructurales.services;

import models.Producto;
import patrones.comportamentales.estados.Orden;
import patrones.creacionales.OrdenBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Servicio especializado para gestión de órdenes.
 * Extrae la responsabilidad de manejo de órdenes del Facade.
 *
 * Responsabilidades:
 * - Gestionar órdenes temporales (en construcción)
 * - Gestionar órdenes activas (procesadas pero no entregadas)
 * - Buscar órdenes por ID
 * - Consultar estado de órdenes
 *
 * PRINCIPIO SRP: Una sola responsabilidad = Gestión de Órdenes
 */
public class OrderService {

    // Órdenes finalizadas pero no entregadas
    private List<Orden> ordenesActivas;

    // Órdenes en construcción (BUILDER)
    private HashMap<Integer, OrdenBuilder> ordenesTemporales;

    public OrderService() {
        this.ordenesActivas = new ArrayList<>();
        this.ordenesTemporales = new HashMap<>();
    }

    /**
     * Inicia una nueva orden para una mesa
     */
    public void iniciarOrden(int mesa) {
        ordenesTemporales.put(mesa, new OrdenBuilder(mesa));
    }

    /**
     * Agrega un producto a la orden temporal de una mesa
     */
    public void agregarProductoAOrden(int mesa, Producto producto) {
        OrdenBuilder builder = ordenesTemporales.get(mesa);
        if (builder == null) {
            throw new IllegalStateException("No hay orden iniciada para la mesa " + mesa);
        }
        builder.agregarProducto(producto);
    }

    /**
     * Agrega observaciones a la orden temporal
     */
    public void agregarObservaciones(int mesa, String observaciones) {
        OrdenBuilder builder = ordenesTemporales.get(mesa);
        if (builder == null) {
            throw new IllegalStateException("No hay orden iniciada para la mesa " + mesa);
        }
        builder.conObservaciones(observaciones);
    }

    /**
     * Obtiene el builder de una orden temporal
     */
    public OrdenBuilder obtenerOrdenTemporal(int mesa) {
        return ordenesTemporales.get(mesa);
    }

    /**
     * Finaliza y registra una orden
     */
    public Orden finalizarOrden(int mesa) {
        OrdenBuilder builder = ordenesTemporales.get(mesa);
        if (builder == null) {
            throw new IllegalStateException("No hay orden iniciada para la mesa " + mesa);
        }

        Orden orden = builder.construir();
        ordenesActivas.add(orden);
        ordenesTemporales.remove(mesa);

        return orden;
    }

    /**
     * Busca una orden activa por ID
     */
    public Orden buscarOrdenActiva(int idOrden) {
        return ordenesActivas.stream()
                .filter(o -> o.getId() == idOrden)
                .findFirst()
                .orElse(null);
    }

    /**
     * Elimina una orden de las órdenes activas
     */
    public void removerOrdenActiva(Orden orden) {
        ordenesActivas.remove(orden);
    }

    /**
     * Obtiene todas las órdenes activas
     */
    public List<Orden> obtenerOrdenesActivas() {
        return new ArrayList<>(ordenesActivas);
    }

    /**
     * Muestra todas las órdenes activas
     */
    public void mostrarOrdenesActivas() {
        System.out.println("\n--- ÓRDENES ACTIVAS ---");

        if (ordenesActivas.isEmpty()) {
            System.out.println("  No hay órdenes activas.\n");
            return;
        }

        ordenesActivas.forEach(System.out::println);
    }
}