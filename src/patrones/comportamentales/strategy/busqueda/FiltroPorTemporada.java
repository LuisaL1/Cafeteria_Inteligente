package patrones.comportamentales.strategy.busqueda;

import models.Producto;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Estrategia que filtra productos según su temporada.
 *
 * Cada producto puede estar asociado a uno o varios meses del año en los que
 * está disponible. Esta clase interpreta un mes recibido como texto y retorna
 * únicamente los productos cuya temporada incluya ese mes.
 */
public class FiltroPorTemporada implements EstrategiaFiltrado<Producto> {

    /**
     * Filtra productos dependiendo del mes indicado.
     *
     * @param productos Lista completa de productos.
     * @param mesStr    Mes en formato de texto (ej: "enero", "FEBRUARY").
     * @return Lista de productos cuya temporada coincide con el mes.
     *
     * El proceso es elegante:
     * - Convertimos el texto a un valor enum Month (estándar de Java).
     * - Usamos Stream para dejar pasar únicamente los productos que contengan ese mes.
     *
     * Si el mes no existe, se lanza una excepción clara para evitar confusiones.
     */
    @Override
    public List<Producto> filtrar(List<Producto> productos, String mesStr) {
        try {
            // Convertimos el string del mes a un valor del enum Month.
            // toUpperCase garantiza compatibilidad sin importar cómo lo escriban.
            Month mes = Month.valueOf(mesStr.toUpperCase());

            // Filtrado fluido y preciso: solo los productos cuya temporada incluya el mes.
            return productos.stream()
                    .filter(p -> p.getTemporada().contains(mes))
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException e) {
            // Si el texto no coincide con ningún mes válido, lo informamos claramente.
            throw new IllegalArgumentException("Mes no válido: " + mesStr);
        }
    }

    /**
     * Nombre del parámetro esperado para esta estrategia.
     * Sirve para identificar qué tipo de filtro aplica esta implementación.
     */
    @Override
    public String getNombreParametro() {
        return "mes";
    }
}