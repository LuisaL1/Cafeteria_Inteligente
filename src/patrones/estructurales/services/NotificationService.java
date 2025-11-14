package patrones.estructurales.services;

import patrones.comportamentales.estados.Orden;
import patrones.comportamentales.observer.Observador;
import patrones.comportamentales.observer.SistemaNotificaciones;

/**
 * Servicio especializado para gestión de notificaciones.
 * Wrapper del patrón Observer para separar responsabilidades.
 *
 * Responsabilidades:
 * - Gestionar el sistema de notificaciones
 * - Registrar observadores
 * - Notificar eventos del sistema
 *
 * PRINCIPIO SRP: Una sola responsabilidad = Notificaciones
 */
public class NotificationService {

    private SistemaNotificaciones sistemaNotificaciones;

    public NotificationService() {
        this.sistemaNotificaciones = new SistemaNotificaciones();
    }

    /**
     * Registra un nuevo observador en el sistema
     */
    public void registrarObservador(Observador observador) {
        sistemaNotificaciones.agregarObservador(observador);
    }

    /**
     * Notifica un evento relacionado con una orden
     */
    public void notificar(Orden orden, String tipoEvento) {
        sistemaNotificaciones.notificar(orden, tipoEvento);
    }
}
