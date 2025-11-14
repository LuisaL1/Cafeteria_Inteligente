package models;

import java.time.Month;
import java.util.List;

public class Comida extends Producto {
    private boolean esVegetariana;

    // Constructor original (compatibilidad)
    public Comida(String nombre, double precio, boolean esVegetariana) {
        super(nombre, precio, "Comida");
        this.esVegetariana = esVegetariana;
    }

    // Constructor completo (nuevo)
    public Comida(String nombre, double precio, String categoria,
                  List<Month> temporada, boolean esNovedad,
                  boolean esPopular, List<String> etiquetas, boolean esVegetariana) {
        super(nombre, precio, categoria, temporada, esNovedad, esPopular, etiquetas);
        this.esVegetariana = esVegetariana;
    }

    public boolean isVegetariana() {
        return esVegetariana;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String tipo = esVegetariana ? "(Vegetariana)" : "";
        sb.append(String.format("%s %s - $%.2f", nombre, tipo, precio));

        if (esNovedad) {
            sb.append(" 🆕");
        }
        if (esPopular) {
            sb.append(" ⭐");
        }

        return sb.toString();
    }
}