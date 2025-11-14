package models;

public abstract class Producto {
    protected String nombre;
    protected double precio;
    protected String categoria;

    public Producto(String nombre, double precio, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return String.format("%s - $%.2f", nombre, precio);
    }
}
