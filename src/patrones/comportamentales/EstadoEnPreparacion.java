package patrones.comportamentales;
// Paquete donde se definen las clases que implementan el patrón State

import models.Orden;
// Importa la clase Orden para poder modificar su estado

public class EstadoEnPreparacion implements StateOrden {
    // Estado que representa una orden que está siendo preparada

    public static final String ESTADO_ID = "EN_PREPARACION";
    // Identificador único del estado

    private static final String DESCRIPCION = "La orden está siendo preparada";
    // Descripción legible del estado

    @Override
    public void siguiente(Orden orden) {
        // Avanza al siguiente estado lógico: LISTO
        orden.setEstado(new EstadoListo());
    }

    @Override
    public void anterior(Orden orden) {
        // Permite regresar al estado anterior: PENDIENTE
        orden.setEstado(new EstadoPendiente());
    }

    @Override
    public String getEstadoId() {
        // Retorna el ID del estado
        return ESTADO_ID;
    }

    @Override
    public String getDescripcion() {
        // Devuelve la descripción del estado
        return DESCRIPCION;
    }

    @Override
    public boolean esTransicionValida(String estadoId) {
        // Solo permite transiciones hacia:
        // - PENDIENTE (retroceder)
        // - LISTO (avanzar)
        return EstadoPendiente.ESTADO_ID.equals(estadoId) ||
                EstadoListo.ESTADO_ID.equals(estadoId);
    }
}

