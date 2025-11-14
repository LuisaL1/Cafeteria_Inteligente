package models;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public abstract class Producto {
    protected String nombre;
    protected double precio;
    protected String categoria;
    protected List<Month> temporada;
    protected List<String> etiquetas;
    protected boolean esNovedad;
    protected boolean esPopular;

    // Constructor original (mantiene compatibilidad)
    public Producto(String nombre, double precio, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.temporada = new ArrayList<>();
        this.etiquetas = new ArrayList<>();
        this.esNovedad = false;
        this.esPopular = false;
    }

    // Constructor completo (nuevo)
    public Producto(String nombre, double precio, String categoria,
                    List<Month> temporada, boolean esNovedad,
                    boolean esPopular, List<String> etiquetas) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.temporada = temporada != null ? new ArrayList<>(temporada) : new ArrayList<>();
        this.etiquetas = etiquetas != null ? new ArrayList<>(etiquetas) : new ArrayList<>();
        this.esNovedad = esNovedad;
        this.esPopular = esPopular;
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

    public List<Month> getTemporada() {
        return new ArrayList<>(temporada);
    }

    public List<String> getEtiquetas() {
        return new ArrayList<>(etiquetas);
    }

    public boolean isEsNovedad() {
        return esNovedad;
    }

    public boolean isEsPopular() {
        return esPopular;
    }

    public void setEsNovedad(boolean esNovedad) {
        this.esNovedad = esNovedad;
    }

    public void setEsPopular(boolean esPopular) {
        this.esPopular = esPopular;
    }

    public void agregarMesTemporada(Month mes) {
        if (!temporada.contains(mes)) {
            temporada.add(mes);
        }
    }

    public void agregarEtiqueta(String etiqueta) {
        if (!etiquetas.contains(etiqueta)) {
            etiquetas.add(etiqueta);
        }
    }

    public void setTemporada(List<Month> temporada) {
        this.temporada = temporada != null ? new ArrayList<>(temporada) : new ArrayList<>();
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas != null ? new ArrayList<>(etiquetas) : new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s - $%.2f", nombre, precio));

        if (esNovedad) {
            sb.append(" 🆕");
        }
        if (esPopular) {
            sb.append(" ⭐");
        }

        return sb.toString();
    }
}