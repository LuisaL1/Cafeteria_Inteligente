package patrones.creacionales;

import models.Producto;
import patrones.comportamentales.strategy.busqueda.EstrategiaFiltrado;
import patrones.comportamentales.strategy.busqueda.FiltroPorCategoria;
import patrones.comportamentales.strategy.busqueda.FiltroPorEtiqueta;
import patrones.comportamentales.strategy.busqueda.FiltroPorTemporada;

import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Implementación de un menú utilizando el patrón Singleton.
 * Incluye funcionalidad para filtrar productos usando el patrón Strategy.
 */
public class MenuSingleton {
    // Instancia volatile para asegurar visibilidad entre hilos.
    private static volatile MenuSingleton instance;
    // Lock explícito para la inicialización segura (double-checked locking).
    private static final Lock lock = new ReentrantLock();

    // Mapa concurrente para almacenar categorías y sus listas de productos.
    private final Map<String, List<Producto>> menu;
    // Mapa de estrategias de filtrado (Strategy pattern).
    private final Map<String, EstrategiaFiltrado<Producto>> estrategiasFiltrado;

    // Constructor privado para forzar el uso del singleton.
    private MenuSingleton() {
        this.menu = new ConcurrentHashMap<>();
        this.estrategiasFiltrado = new HashMap<>();
        inicializarEstrategias();
        inicializarMenu();
    }

    // Método público para obtener la instancia única (thread-safe).
    public static MenuSingleton getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new MenuSingleton();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * Método de compatibilidad con código existente.
     * @return La instancia única del menú
     */
    public static MenuSingleton getInstancia() {
        return getInstance();
    }

    private void inicializarEstrategias() {
        // Registrar estrategias de filtrado
        registrarEstrategia("categoria", new FiltroPorCategoria());
        registrarEstrategia("temporada", new FiltroPorTemporada());
        registrarEstrategia("etiqueta", new FiltroPorEtiqueta());
    }

    private void inicializarMenu() {
        // Inicialización del menú con categorías y productos de ejemplo
        List<Producto> bebidas = List.of(
                new Bebida("Café Americano", 25.0, "Bebidas",
                        List.of(Month.JANUARY, Month.FEBRUARY, Month.DECEMBER),
                        false, true, List.of("caliente")),
                new Bebida("Té Helado", 30.0, "Bebidas",
                        List.of(Month.JUNE, Month.JULY, Month.AUGUST),
                        true, false, List.of("frío", "refrescante"))
        );

        List<Producto> comidas = List.of(
                new Comida("Ensalada César", 120.0, "Comidas",
                        List.of(Month.MARCH, Month.APRIL, Month.MAY),
                        true, true, List.of("ensalada", "fresco")),
                new Comida("Sopa de Calabaza", 95.0, "Comidas",
                        List.of(Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER),
                        false, false, List.of("caliente", "invierno"))
        );

        menu.put("Bebidas", new ArrayList<>(bebidas));
        menu.put("Comidas", new ArrayList<>(comidas));
    }

    public void registrarEstrategia(String nombre, EstrategiaFiltrado<Producto> estrategia) {
        estrategiasFiltrado.put(nombre.toLowerCase(), estrategia);
    }

    public List<Producto> filtrarProductos(String estrategia, String parametro) {
        EstrategiaFiltrado<Producto> filtro = estrategiasFiltrado.get(estrategia.toLowerCase());
        if (filtro == null) {
            throw new IllegalArgumentException("Estrategia de filtrado no encontrada: " + estrategia);
        }

        List<Producto> todosLosProductos = new ArrayList<>();
        menu.values().forEach(todosLosProductos::addAll);

        return filtro.filtrar(todosLosProductos, parametro);
    }

    public List<Producto> filtrarProductosAvanzado(Map<String, String> filtros) {
        List<Producto> resultado = new ArrayList<>();
        menu.values().forEach(resultado::addAll);

        for (Map.Entry<String, String> entrada : filtros.entrySet()) {
            EstrategiaFiltrado<Producto> filtro = estrategiasFiltrado.get(entrada.getKey().toLowerCase());
            if (filtro != null) {
                resultado = filtro.filtrar(resultado, entrada.getValue());
            }
        }

        return resultado;
    }

    public Map<String, List<Producto>> getMenu() {
        Map<String, List<Producto>> copia = new HashMap<>();
        menu.forEach((k, v) -> copia.put(k, new ArrayList<>(v)));
        return copia;
    }

    public List<Producto> getCategoria(String categoria) {
        return new ArrayList<>(menu.getOrDefault(categoria, new ArrayList<>()));
    }

    public void agregarProducto(String categoria, Producto producto) {
        menu.computeIfAbsent(categoria, k -> new ArrayList<>()).add(producto);
    }

    /**
     * Muestra el menú completo en la consola, organizado por categorías.
     */
    private static final Logger logger = Logger.getLogger(MenuSingleton.class.getName());

    public void mostrarMenu() {
        StringBuilder menuBuilder = new StringBuilder();
        menuBuilder.append("\n╔═══════════════════════════════════════════╗\n");
        menuBuilder.append("║              MENÚ PRINCIPAL               ║\n");
        menuBuilder.append("╚═══════════════════════════════════════════╝\n\n");

        menu.forEach((categoria, productos) -> {
            menuBuilder.append("▸ ").append(categoria).append(":\n");
            int i = 1;
            for (Producto producto : productos) {
                menuBuilder.append(String.format("  %d. %s%n", i++, producto));
            }
            menuBuilder.append("\n");
        });

        logger.info(menuBuilder.toString());
    }

    // Método para manejar la deserialización correctamente
    protected Object readResolve() {
        return getInstance();
    }

    // Clases internas para los tipos de productos
    // Clases internas para los tipos de productos
    public static class Bebida extends Producto {
        public Bebida(String nombre, double precio, String categoria,
                      List<Month> temporada, boolean esNovedad,
                      boolean esPopular, List<String> etiquetas) {
            super(nombre, precio, categoria, temporada, esNovedad, esPopular, etiquetas);
        }
    }

    public static class Comida extends Producto {
        public Comida(String nombre, double precio, String categoria,
                      List<Month> temporada, boolean esNovedad,
                      boolean esPopular, List<String> etiquetas) {
            super(nombre, precio, categoria, temporada, esNovedad, esPopular, etiquetas);
        }
    }

    public static class Postre extends Producto {
        public Postre(String nombre, double precio, String categoria,
                      List<Month> temporada, boolean esNovedad,
                      boolean esPopular, List<String> etiquetas) {
            super(nombre, precio, categoria, temporada, esNovedad, esPopular, etiquetas);
        }
    }

    public static class Entrada extends Producto {
        public Entrada(String nombre, double precio, String categoria,
                       List<Month> temporada, boolean esNovedad,
                       boolean esPopular, List<String> etiquetas) {
            super(nombre, precio, categoria, temporada, esNovedad, esPopular, etiquetas);
        }
    }
}
