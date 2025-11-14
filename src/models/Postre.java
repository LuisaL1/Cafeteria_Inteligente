package models;

public class Postre extends Producto {
    private boolean sinAzucar;

    public Postre(String nombre, double precio, boolean sinAzucar) {
        super(nombre, precio, "Postre");
        this.sinAzucar = sinAzucar;
    }

    public boolean isSinAzucar() {
        return sinAzucar;
    }

    @Override
    public String toString() {
        String tipo = sinAzucar ? "(Sin azúcar)" : "";
        return String.format("%s %s - $%.2f", nombre, tipo, precio);
    }
}