package patrones.comportamentales.estados;

/**
 * Representa el estado "Cancelado" dentro del patrón State para una orden.
 * Una vez aquí… la orden básicamente se fue a dormir para siempre.
 */
public class EstadoCancelado implements StateOrden {

    // Identificador del estado dentro del flujo
    public static final String ESTADO_ID = "CANCELADO";

    // Descripción amigable del estado
    private static final String DESCRIPCION = "La orden ha sido cancelada";

    @Override
    public void siguiente(Orden orden) {
        // Como buen punto final, este estado no permite avanzar.
        // Sería como intentar revivir un café que ya se cayó al piso.
        throw new IllegalStateException("No se puede avanzar desde el estado CANCELADO");
    }

    @Override
    public void anterior(Orden orden) {
        // Dependiendo de la lógica de negocio, podríamos permitir reactivar una orden,
        // pero aquí se asume que no hay regreso: lo cancelado, cancelado está.
        throw new IllegalStateException("No se puede retroceder desde el estado CANCELADO");
    }

    @Override
    public String getEstadoId() {
        return ESTADO_ID;
    }

    @Override
    public String getDescripcion() {
        return DESCRIPCION;
    }

    @Override
    public boolean esTransicionValida(String estadoId) {
        // En este modelo, el estado cancelado es un abismo sin salida:
        // no acepta transiciones hacia ningún otro estado.
        return false;
    }
}
