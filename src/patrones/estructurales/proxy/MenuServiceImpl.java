package patrones.estructurales.proxy;

import models.Producto;
import patrones.creacionales.MenuSingleton;

import java.util.List;

public class MenuServiceImpl implements MenuService {

    private MenuSingleton menu;

    public MenuServiceImpl() {
        this.menu = MenuSingleton.getInstancia();
        System.out.println("Inicializando servicio de menú...");
    }

    @Override
    public List<Producto> obtenerBebidas() {
        System.out.println("Consultando bebidas desde el origen...");
        return menu.getCategoria("Bebidas");
    }

    @Override
    public List<Producto> obtenerComidas() {
        System.out.println("Consultando comidas desde el origen...");
        return menu.getCategoria("Comidas");
    }

    @Override
    public List<Producto> obtenerPostres() {
        System.out.println("Consultando postres desde el origen...");
        return menu.getCategoria("Postres");
    }

    @Override
    public void mostrarMenu() {
        System.out.println("Generando menú completo desde el origen...");
        menu.mostrarMenu();
    }

    @Override
    public void invalidarCache() {
        // Implementación vacía porque el servicio real no maneja caché
    }
}