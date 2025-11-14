package patrones.comportamentales.estados;
// Paquete donde se encuentra la clase Orden

import models.Producto;
// Importación de las clases que forman parte del patrón State

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
// Importación de utilidades y tipos de fecha

/**
 * Clase que representa una orden en el sistema de la cafetería.
 * Utiliza el patrón State para gestionar el ciclo de vida de la orden.
 */
public class Orden {
    private static int contadorId = 1;
    // Contador estático para generar IDs incrementales

    private final int id;
    // ID único de la orden

    private final int numeroMesa;
    // Número de mesa asociado a la orden

    private final List<Producto> productos;
    // Lista de productos agregados a la orden

    private StateOrden estado;
    // Estado actual de la orden (Patrón State)

    private final LocalDateTime fechaCreacion;
    // Fecha en la que se crea la orden

    private LocalDateTime fechaActualizacion;
    // Fecha en la que se modifica por última vez la orden

    private String observaciones;
    // Observaciones adicionales

    private String motivoCancelacion;
    // Motivo por el cual se cancela la orden

    /**
     * Crea una nueva orden para la mesa especificada.
     * @param numeroMesa Número de mesa para la orden
     * @throws IllegalArgumentException si el número de mesa es inválido
     */
    public Orden(int numeroMesa) {
        if (numeroMesa <= 0) {
            throw new IllegalArgumentException("El número de mesa debe ser mayor que cero");
        }
        // Validación del número de mesa

        this.id = contadorId++;
        // Genera un ID único para la orden

        this.numeroMesa = numeroMesa;
        this.productos = new ArrayList<>();
        // Inicializa la lista de productos

        this.estado = new EstadoPendiente();
        // Estado inicial: pendiente

        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = this.fechaCreacion;
        // Al inicio tienen la misma fecha

        this.observaciones = "";
        this.motivoCancelacion = "";
    }

    /**
     * Agrega un producto a la orden.
     * @param producto Producto a agregar
     * @throws IllegalStateException si la orden está cancelada o entregada
     */
    public void agregarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        // Evita agregar productos en estados finales
        if (estado.getEstadoId().equals("CANCELADO") || estado.getEstadoId().equals("ENTREGADO")) {
            throw new IllegalStateException("No se pueden agregar productos a una orden " + estado.getEstadoId());
        }

        productos.add(producto);
        // Agrega el producto

        actualizarFechaModificacion();
        // Actualiza fecha de actualización
    }

    /**
     * Establece observaciones para la orden.
     * @param obs Observaciones a establecer
     */
    public void setObservaciones(String obs) {
        this.observaciones = Objects.requireNonNullElse(obs, "");
        actualizarFechaModificacion();
    }

    /**
     * Calcula el total de la orden.
     * @return El monto total de la orden
     */
    public double calcularTotal() {
        return productos.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();
        // Suma los precios de todos los productos
    }

    /**
     * Avanza el estado de la orden al siguiente estado lógico.
     */
    public void avanzarEstado() {
        estado.siguiente(this);
        // Delegación al estado actual

        actualizarFechaModificacion();
    }

    /**
     * Retrocede el estado de la orden al estado anterior.
     */
    public void retrocederEstado() {
        estado.anterior(this);
        // Lógica inversa del patrón State

        actualizarFechaModificacion();
    }

    /**
     * Cancela la orden con un motivo específico.
     * @param motivo El motivo de la cancelación
     */
    public void cancelar(String motivo) {
        if (estado.getEstadoId().equals("CANCELADO")) {
            throw new IllegalStateException("La orden ya está cancelada");
        }

        if (estado.getEstadoId().equals("ENTREGADO")) {
            throw new IllegalStateException("No se puede cancelar una orden ya entregada");
        }

        // Registra motivo de cancelación y asigna el nuevo estado
        this.motivoCancelacion = Objects.requireNonNullElse(motivo, "Sin motivo especificado");
        this.estado = new EstadoCancelado();
        actualizarFechaModificacion();
    }

    /**
     * Establece un nuevo estado para la orden.
     * Este método es utilizado por las clases de estado para realizar transiciones.
     */
    public void setEstado(StateOrden nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }

        // Valida si la transición es correcta según la regla del patrón State
        if (this.estado != null && !this.estado.esTransicionValida(nuevoEstado.getEstadoId())) {
            throw new IllegalStateException(String.format(
                    "Transición inválida de %s a %s",
                    this.estado.getEstadoId(),
                    nuevoEstado.getEstadoId()
            ));
        }

        this.estado = nuevoEstado;
        actualizarFechaModificacion();
    }

    // Getters básicos para atributos importantes
    public int getId() { return id; }
    public int getNumeroMesa() { return numeroMesa; }
    public List<Producto> getProductos() { return Collections.unmodifiableList(productos); }
    public String getEstado() { return estado.getEstadoId(); }
    public String getDescripcionEstado() { return estado.getDescripcion(); }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public String getObservaciones() { return observaciones; }
    public String getMotivoCancelacion() { return motivoCancelacion; }

    /**
     * Verifica si la orden puede realizar una transición al estado especificado.
     */
    public boolean esTransicionValida(String estadoId) {
        return estado.esTransicionValida(estadoId);
    }

    /**
     * Actualiza la fecha de modificación de la orden.
     */
    private void actualizarFechaModificacion() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Representación detallada de la orden en formato tipo "ticket".
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();

        // Encabezado del ticket
        sb.append(String.format("%n┌─────────────────────────────────────────┐%n"));
        sb.append(String.format("│ ORDEN #%03d - Mesa %d                  │%n", id, numeroMesa));
        sb.append(String.format("│ Estado: %-30s │%n", getEstado()));
        sb.append(String.format("│ Descripción: %-25s │%n", getDescripcionEstado()));
        sb.append(String.format("│ Fecha: %-31s │%n", fechaCreacion.format(formatter)));
        sb.append(String.format("│ Actualizado: %-27s │%n", fechaActualizacion.format(formatter)));

        // Si hay motivo de cancelación, se imprime
        if (!motivoCancelacion.isEmpty()) {
            sb.append(String.format("│ Motivo cancelación: %-19s │%n",
                    motivoCancelacion.substring(0, Math.min(motivoCancelacion.length(), 20)) +
                            (motivoCancelacion.length() > 20 ? "..." : "")));
        }

        sb.append(String.format("├─────────────────────────────────────────┤%n"));

        // Listado de productos
        for (Producto p : productos) {
            String nombre = p.getNombre();
            // Acorta nombres muy largos
            String nombreFormateado = (nombre.length() > 33) ?
                    nombre.substring(0, 30) + "..." :
                    nombre;

            sb.append(String.format("│ • %-36s │%n",
                    String.format("%-33s $%6.2f", nombreFormateado, p.getPrecio())));
        }

        sb.append(String.format("├─────────────────────────────────────────┤%n"));
        sb.append(String.format("│ %-35s %6.2f │%n", "TOTAL:", calcularTotal()));
        sb.append(String.format("└─────────────────────────────────────────┘%n"));

        return sb.toString();
    }
}

