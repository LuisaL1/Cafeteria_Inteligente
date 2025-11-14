package models;

public class Comida extends Producto {
    private boolean esVegetariana;

    public Comida(String nombre, double precio, boolean esVegetariana) {
        super(nombre, precio, "Comida");
        this.esVegetariana = esVegetariana;
    }

    public boolean isVegetariana() {
        return esVegetariana;
    }

    @Override
    public String toString() {
        String tipo = esVegetariana ? "(Vegetariana)" : "";
        return String.format("%s %s - $%.2f", nombre, tipo, precio);
    }
}