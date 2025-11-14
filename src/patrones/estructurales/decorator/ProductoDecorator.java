package patrones.estructurales.decorator;

import models.Producto;

public abstract class ProductoDecorator extends Producto {
    protected Producto producto;

    public ProductoDecorator(Producto producto) {
        super(producto.getNombre(), producto.getPrecio(), producto.getCategoria());
        this.producto = producto;
    }
}