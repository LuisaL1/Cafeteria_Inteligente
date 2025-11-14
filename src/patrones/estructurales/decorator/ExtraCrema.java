package patrones.estructurales.decorator;

import models.Producto;

public class ExtraCrema extends ProductoDecorator {
    public ExtraCrema(Producto producto) {
        super(producto);
        this.precio = producto.getPrecio() + 0.50;
    }

    @Override
    public String getNombre() {
        return producto.getNombre() + " + Crema";
    }
}