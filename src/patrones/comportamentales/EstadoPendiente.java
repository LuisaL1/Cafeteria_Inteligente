package patrones.comportamentales;
// Paquete donde se define la lógica de los estados (patrón State)

import models.Orden;
// Importa la clase Orden, necesaria para modificar su estado

public class EstadoPendiente implements StateOrden {
    // Clase que representa el estado inicial de una orden (PENDIENTE)

    public static final String ESTADO_ID = "PENDIENTE";
    // Identificador único del estado

    private static final String DESCRIPCION = "La orden está pendiente de ser preparada";
    // Descripción legible del estado

    @Override
    public void siguiente(Orden orden) {
        // Avanza al siguiente estado lógico: En Preparación
        orden.setEstado(new EstadoEnPreparacion());
    }

    @Override
    public void anterior(Orden orden) {
        // No es posible retroceder desde el estado inicial
        throw new IllegalStateException("No se puede retroceder desde el estado PENDIENTE");
    }

    @Override
    public String getEstadoId() {
        // Retorna el ID del estado actual
        return ESTADO_ID;
    }

    @Override
    public String getDescripcion() {
        // Devuelve la descripción del estado
        return DESCRIPCION;
    }

    @Override
    public boolean esTransicionValida(String estadoId) {
        // Solo es válida la transición hacia "EN_PREPARACION"
        return EstadoEnPreparacion.ESTADO_ID.equals(estadoId);
    }
}

