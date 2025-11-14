package patrones.estructurales.services;

import patrones.comportamentales.estados.Orden;
import patrones.comportamentales.MementoHistorial;

/**
 * Servicio especializado para gestión de historial.
 * Wrapper del patrón Memento para separar responsabilidades.
 *
 * Responsabilidades:
 * - Guardar órdenes completadas en el historial
 * - Consultar historial de órdenes
 *
 * PRINCIPIO SRP: Una sola responsabilidad = Historial
 */
public class HistoryService {

    private MementoHistorial historial;

    public HistoryService() {
        this.historial = MementoHistorial.getInstancia();
    }

    /**
     * Guarda una orden en el historial
     */
    public void guardarOrden(Orden orden) {
        historial.guardarOrden(orden);
    }

    /**
     * Muestra el historial completo de órdenes
     */
    public void mostrarHistorial() {
        historial.mostrarHistorial();
    }
}
