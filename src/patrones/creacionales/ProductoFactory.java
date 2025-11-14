package patrones.creacionales;

import models.*;

public class ProductoFactory {

    public static Producto crearProducto(String tipo, String nombre, double precio) {
        switch (tipo.toUpperCase()) {
            case "BEBIDA":
                return new Bebida(nombre, precio, "Mediano");

            case "COMIDA":
                return new Comida(nombre, precio, false);

            case "POSTRE":
                return new Postre(nombre, precio, false);

            case "ENTRADA":
                return new Entrada(nombre, precio);

            default:
                throw new IllegalArgumentException(
                        "Tipo de producto desconocido: " + tipo
                );
        }
    }

    /**
     * Sobrecarga: Crea una bebida con tamaño específico
     */
    public static Producto crearBebida(String nombre, double precio, String tamanio) {
        return new Bebida(nombre, precio, tamanio);
    }

    /**
     * Sobrecarga: Crea una comida especificando si es vegetariana
     */
    public static Producto crearComida(String nombre, double precio, boolean esVegetariana) {
        return new Comida(nombre, precio, esVegetariana);
    }

    /**
     * Sobrecarga: Crea un postre especificando si es sin azúcar
     */
    public static Producto crearPostre(String nombre, double precio, boolean sinAzucar) {
        return new Postre(nombre, precio, sinAzucar);
    }
}
