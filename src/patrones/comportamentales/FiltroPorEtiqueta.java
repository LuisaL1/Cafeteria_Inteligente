package patrones.comportamentales;

import models.Producto;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Estrategia que permite filtrar productos según una etiqueta específica.
 *
 * Muchos productos pueden tener etiquetas como "vegano", "sin gluten",
 * "picante", "artesanal", etc. Esta clase busca dentro de esas etiquetas
 * y devuelve únicamente los productos que contengan la etiqueta indicada.
 */
public class FiltroPorEtiqueta implements EstrategiaFiltrado<Producto> {

    /**
     * Filtra una lista de productos verificando si contienen la etiqueta dada.
     *
     * @param productos Lista de productos a analizar.
     * @param etiqueta  Etiqueta buscada (sin importar mayúsculas o minúsculas).
     * @return Productos que poseen al menos una etiqueta que coincide con la buscada.
     *
     * La danza interna:
     * - Por cada producto, revisamos su lista de etiquetas.
     * - Si alguna coincide (ignorando mayúsculas), el producto pasa el filtro.
     * - Stream + anyMatch = elegancia, velocidad y precisión.
     */
    @Override
    public List<Producto> filtrar(List<Producto> productos, String etiqueta) {
        return productos.stream()
                .filter(p -> p.getEtiquetas().stream()
                        .anyMatch(e -> e.equalsIgnoreCase(etiqueta)))  // ¿Tiene la etiqueta? Pasa.
                .collect(Collectors.toList());
    }

    /**
     * Indica el nombre del parámetro que esta estrategia espera recibir.
     * Útil para integrar con menús o controles de UI dinámicos.
     */
    @Override
    public String getNombreParametro() {
        return "etiqueta";
    }
}

