package models;

import java.time.Month;
import java.util.List;

public class Postre extends Producto {
    private boolean sinAzucar;

    // Constructor original (compatibilidad)
    public Postre(String nombre, double precio, boolean sinAzucar) {
        super(nombre, precio, "Postre");
        this.sinAzucar = sinAzucar;
    }

    // Constructor completo (nuevo)
    public Postre(String nombre, double precio, String categoria,
                  List<Month> temporada, boolean esNovedad,
                  boolean esPopular, List<String> etiquetas, boolean sinAzucar) {
        super(nombre, precio, categoria, temporada, esNovedad, esPopular, etiquetas);
        this.sinAzucar = sinAzucar;
    }

    public boolean isSinAzucar() {
        return sinAzucar;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String tipo = sinAzucar ? "(Sin azúcar)" : "";
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