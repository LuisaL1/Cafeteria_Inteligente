package models;

public class Bebida extends Producto {
    private String tamanio;

    public Bebida(String nombre, double precio, String tamanio) {
        super(nombre, precio, "Bebida");
        this.tamanio = tamanio;
    }

    public String getTamanio() {
        return tamanio;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - $%.2f", nombre, tamanio, precio);
    }
}
