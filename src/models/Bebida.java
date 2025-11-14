package models;

import java.time.Month;
import java.util.List;

public class Bebida extends Producto {
    private String tamanio;

    // Constructor original (compatibilidad)
    public Bebida(String nombre, double precio, String tamanio) {
        super(nombre, precio, "Bebida");
        this.tamanio = tamanio;
    }

    // Constructor completo (nuevo)
    public Bebida(String nombre, double precio, String categoria,
                  List<Month> temporada, boolean esNovedad,
                  boolean esPopular, List<String> etiquetas, String tamanio) {
        super(nombre, precio, categoria, temporada, esNovedad, esPopular, etiquetas);
        this.tamanio = tamanio;
    }

    public String getTamanio() {
        return tamanio;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s (%s) - $%.2f", nombre, tamanio, precio));

        if (esNovedad) {
            sb.append(" 🆕");
        }
        if (esPopular) {
            sb.append(" ⭐");
        }

        return sb.toString();
    }
}