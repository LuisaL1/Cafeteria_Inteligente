package patrones.estructurales.proxy;

import models.Producto;

import java.util.ArrayList;
import java.util.List;

public class MenuServiceProxy implements MenuService {

    // Servicio real al que delegar cuando no hay caché
    private MenuService servicioReal;

    // Caché interna (memoria interna)
    private List<Producto> cacheBebidas;
    private List<Producto> cacheComidas;
    private List<Producto> cachePostres;

    private boolean cacheInicializada = false;

    public MenuServiceProxy() {
        System.out.println("Proxy de menú inicializado (con caché interna)");
    }

    private MenuService obtenerServicioReal() {
        if (servicioReal == null) {
            servicioReal = new MenuServiceImpl();
        }
        return servicioReal;
    }

    private void cargarCache() {
        System.out.println("Cargando caché de menú...");
        MenuService servicio = obtenerServicioReal();

        // Copiar las listas para evitar modificaciones externas
        this.cacheBebidas = new ArrayList<>(servicio.obtenerBebidas());
        this.cacheComidas = new ArrayList<>(servicio.obtenerComidas());
        this.cachePostres = new ArrayList<>(servicio.obtenerPostres());
        this.cacheInicializada = true;

        System.out.println("Caché cargada exitosamente");
    }

    @Override
    public List<Producto> obtenerBebidas() {
        if (!cacheInicializada) {
            cargarCache();
        }
        System.out.println("Sirviendo bebidas desde CACHÉ");
        return new ArrayList<>(cacheBebidas);
    }

    @Override
    public List<Producto> obtenerComidas() {
        if (!cacheInicializada) {
            cargarCache();
        }
        System.out.println("Sirviendo comidas desde CACHÉ");
        return new ArrayList<>(cacheComidas);
    }

    @Override
    public List<Producto> obtenerPostres() {
        if (!cacheInicializada) {
            cargarCache();
        }
        System.out.println("Sirviendo postres desde CACHÉ");
        return new ArrayList<>(cachePostres);
    }

    @Override
    public void mostrarMenu() {
        if (!cacheInicializada) {
            cargarCache();
        }

        System.out.println("Mostrando menú desde CACHÉ");
        System.out.println("\n========== MENÚ ==========");

        System.out.println("\n☕ BEBIDAS:");
        cacheBebidas.forEach(b -> System.out.println("  - " + b));

        System.out.println("\n🍴 COMIDAS:");
        cacheComidas.forEach(c -> System.out.println("  - " + c));

        System.out.println("\n🍰 POSTRES:");
        cachePostres.forEach(p -> System.out.println("  - " + p));

        System.out.println("\n==========================\n");
    }

    @Override
    public void invalidarCache() {
        System.out.println("Invalidando caché de menú...");
        this.cacheBebidas = null;
        this.cacheComidas = null;
        this.cachePostres = null;
        this.cacheInicializada = false;
        System.out.println("Caché invalidada. Próxima consulta recargará desde el origen.");
    }
}