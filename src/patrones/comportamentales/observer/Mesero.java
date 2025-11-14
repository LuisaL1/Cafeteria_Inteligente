package patrones.comportamentales.observer;

import patrones.comportamentales.estados.Orden;

public class Mesero implements Observador {
    private String nombre;

    public Mesero(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void actualizar(Orden orden, String evento) {
        if (evento.equals("ORDEN_LISTA")) {
            System.out.println("\n🔔 [MESERO " + nombre + "] Orden lista para entregar #" + orden.getId());
            System.out.println("   Mesa: " + orden.getNumeroMesa());
            System.out.println("   Total: $" + String.format("%.2f", orden.calcularTotal()));
        }
    }
}