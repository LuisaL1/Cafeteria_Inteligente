package patrones.estructurales.decorator;

import models.Producto;

public class ExtraJarabe extends ProductoDecorator {
    private String sabor;

    public ExtraJarabe(Producto producto, String sabor) {
        super(producto);
        this.sabor = sabor;
        this.precio = producto.getPrecio() + 0.75;
    }

    @Override
    public String getNombre() {
        return producto.getNombre() + " + Jarabe de " + sabor;
    }
}

