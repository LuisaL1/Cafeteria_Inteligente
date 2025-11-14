package patrones.comportamentales;
// Paquete donde están las implementaciones del patrón State

import models.Orden;
// Se importa la clase Orden para poder cambiar su estado

public class EstadoEntregado implements StateOrden {
    // Estado que representa que la orden ya fue entregada al cliente

    public static final String ESTADO_ID = "ENTREGADO";
    // Identificador único del estado

    private static final String DESCRIPCION = "La orden ha sido entregada al cliente";
    // Descripción legible del estado

    @Override
    public void siguiente(Orden orden) {
        // No existe un estado posterior al ENTREGADO
        // Por eso se lanza una excepción para indicar transición inválida
        throw new IllegalStateException("No se puede avanzar desde el estado ENTREGADO");
    }

    @Override
    public void anterior(Orden orden) {
        // Permite retroceder al estado LISTO
        // Esto podría usarse si, por error, se marcó como entregada antes de tiempo
        orden.setEstado(new EstadoListo());
    }

    @Override
    public String getEstadoId() {
        // Devuelve el ID del estado actual
        return ESTADO_ID;
    }

    @Override
    public String getDescripcion() {
        // Proporciona una descripción del estado
        return DESCRIPCION;
    }

    @Override
    public boolean esTransicionValida(String estadoId) {
        // Desde ENTREGADO *solo* es válida la transición hacia LISTO (retroceso)
        return EstadoListo.ESTADO_ID.equals(estadoId);
    }
}

