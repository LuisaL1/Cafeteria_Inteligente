package patrones.comportamentales.observer;
import patrones.comportamentales.estados.Orden;
public interface Observador {
    void actualizar(Orden orden, String evento);
}