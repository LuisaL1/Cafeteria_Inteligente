package patrones.comportamentales;

import java.util.List;

/**
 * Interfaz base para implementar el patrón Strategy aplicado al filtrado de productos.
 *
 * Cada estrategia define “cómo mira” la lista de productos: por tipo, por temporada,
 * por disponibilidad… o por cualquier criterio que el negocio necesite.
 *
 * En otras palabras: esta interfaz es la brújula que guía cómo se seleccionan
 * los productos dentro del menú.
 */
public interface EstrategiaFiltrado<T> {

    /**
     * Aplica un criterio de filtrado sobre una lista de productos.
     *
     * @param productos Lista de elementos sobre los que se realizará el filtrado.
     * @param parametro Parámetro que define el criterio. Su interpretación depende de
     *                  cada estrategia concreta (por ejemplo: “BEBIDA”, “NAVIDAD”, etc.).
     * @return Una nueva lista con los productos que cumplen el criterio definido.
     *
     * La gracia del patrón Strategy es que este método siempre se llama igual,
     * pero su comportamiento puede cambiar radicalmente según la estrategia usada.
     * Elegancia pura.
     */
    List<T> filtrar(List<T> productos, String parametro);
}
