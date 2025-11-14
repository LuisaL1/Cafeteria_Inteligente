package models;

import java.time.Month;
import java.util.List;

public class Entrada extends Producto {

    // Constructor original (compatibilidad)
    public Entrada(String nombre, double precio) {
        super(nombre, precio, "Entrada");
    }

    // Constructor completo (nuevo)
    public Entrada(String nombre, double precio, String categoria,
                   List<Month> temporada, boolean esNovedad,
                   boolean esPopular, List<String> etiquetas) {
        super(nombre, precio, categoria, temporada, esNovedad, esPopular, etiquetas);
    }
}