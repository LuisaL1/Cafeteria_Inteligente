package patrones.comportamentales.estados;
// Paquete donde se implementan los estados del patrón State

// Se importa la clase Orden para poder modificar su estado internamente

public class EstadoListo implements StateOrden {
    // Estado que representa una orden que ya está preparada y lista para entrega

    public static final String ESTADO_ID = "LISTO";
    // Identificador único para este estado

    private static final String DESCRIPCION = "La orden está lista para ser entregada";
    // Descripción legible del estado

    @Override
    public void siguiente(Orden orden) {
        // Avanza al siguiente estado lógico: ENTREGADO
        orden.setEstado(new EstadoEntregado());
    }

    @Override
    public void anterior(Orden orden) {
        // Permite retroceder al estado previo: EN_PREPARACION
        orden.setEstado(new EstadoEnPreparacion());
    }

    @Override
    public String getEstadoId() {
        // Devuelve el ID del estado actual
        return ESTADO_ID;
    }

    @Override
    public String getDescripcion() {
        // Retorna la descripción del estado
        return DESCRIPCION;
    }

    @Override
    public boolean esTransicionValida(String estadoId) {
        // Solo permite transiciones hacia:
        // - EN_PREPARACION (retroceder)
        // - ENTREGADO (avanzar)
        return EstadoEnPreparacion.ESTADO_ID.equals(estadoId) ||
                EstadoEntregado.ESTADO_ID.equals(estadoId);
    }
}

