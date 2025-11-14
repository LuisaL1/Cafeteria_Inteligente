package patrones.comportamentales.estados;
// Paquete donde se agrupan las clases relacionadas con el patrón State

// Importa la clase Orden porque los métodos del estado operan sobre ella

public interface StateOrden {
    // Interfaz que define el contrato que todos los estados deben cumplir
    // Representa el patrón State aplicado al ciclo de vida de una orden

    void siguiente(Orden orden);
    // Cambia la orden al siguiente estado lógico
    // Cada implementación define qué significa "siguiente"

    void anterior(Orden orden);
    // Retrocede la orden al estado anterior
    // No todos los estados permiten retroceder, por lo que pueden lanzar excepción

    String getEstadoId();
    // Devuelve un identificador único del estado (por ejemplo: "PENDIENTE")

    String getDescripcion();
    // Retorna una descripción legible del estado, útil para mostrar en interfaz

    boolean esTransicionValida(String estadoId);
    // Verifica si la orden puede cambiar desde el estado actual hacia el estado indicado
    // Permite controlar la lógica de transición y evitar cambios inválidos
}

