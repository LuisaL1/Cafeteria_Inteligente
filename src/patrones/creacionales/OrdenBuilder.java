package patrones.creacionales;

import patrones.comportamentales.estados.Orden;
import models.Producto;

public class OrdenBuilder {
    private Orden orden;

    public OrdenBuilder(int numeroMesa) {
        this.orden = new Orden(numeroMesa);
    }

    public OrdenBuilder agregarProducto(Producto producto) {
        orden.agregarProducto(producto);
        return this;
    }

    public OrdenBuilder conObservaciones(String obs) {
        orden.setObservaciones(obs);
        return this;
    }

    public Orden construir() {
        return orden;
    }
}
