package patrones.comportamentales;

import patrones.comportamentales.estados.Orden;

import java.util.ArrayList;
import java.util.List;

public class MementoHistorial {
    private static MementoHistorial instancia;
    private List<Orden> historial;

    private MementoHistorial() {
        historial = new ArrayList<>();
    }

    public static MementoHistorial getInstancia() {
        if (instancia == null) {
            instancia = new MementoHistorial();
        }
        return instancia;
    }

    public void guardarOrden(Orden orden) {
        historial.add(orden);
    }

    public List<Orden> obtenerHistorial() {
        return new ArrayList<>(historial);
    }

    public void mostrarHistorial() {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║       HISTORIAL DE ÓRDENES                ║");
        System.out.println("╚═══════════════════════════════════════════╝");

        if (historial.isEmpty()) {
            System.out.println("\n  No hay órdenes en el historial.\n");
            return;
        }

        for (Orden orden : historial) {
            System.out.println(orden);
        }
    }
}