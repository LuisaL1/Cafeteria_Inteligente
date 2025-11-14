package patrones.comportamentales;

import models.Producto;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Estrategia concreta que filtra productos según su categoría.
 *
 * Esta clase implementa la lógica del patrón Strategy para seleccionar únicamente
 * los productos que pertenecen a la categoría indicada. Ideal cuando necesitas
 * construir vistas, menús o búsquedas más organizadas.
 */
public class FiltroPorCategoria implements EstrategiaFiltrado<Producto> {

    /**
     * Filtra una lista de productos dejando solo aquellos cuya categoría coincide
     * con la proporcionada.
     *
     * @param productos Lista completa de productos disponibles.
     * @param categoria Categoría por la cual se quiere filtrar (ej: "Bebidas", "Postres").
     * @return Una lista de productos pertenecientes a esa categoría.
     *         Si la categoría es nula o vacía, simplemente retorna la lista original.
     *
     * La magia fluye así: usamos un Stream, dejamos que cada producto “se presente”
     * y solo dejamos pasar a los que encajan con la categoría buscada.
     */
    @Override
    public List<Producto> filtrar(List<Producto> productos, String categoria) {
        // Si no se especifica una categoría, devolvemos la lista tal cual.
        if (categoria == null || categoria.trim().isEmpty()) {
            return productos;
        }

        // Filtrado elegante usando Stream: claridad, precisión, futuro.
        return productos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }
}
