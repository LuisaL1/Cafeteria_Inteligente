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
        return menu.obtenerCategoria("BEBIDA");
    }

    @Override
    public List<Producto> obtenerComidas() {
        System.out.println("Consultando comidas desde el origen...");
        return menu.obtenerCategoria("COMIDA");
    }

    @Override
    public List<Producto> obtenerPostres() {
        System.out.println("Consultando postres desde el origen...");
        return menu.obtenerCategoria("POSTRE");
    }

    @Override
    public void mostrarMenu() {
        System.out.println("Generando menú completo desde el origen...");
        menu.mostrarMenu();
    }

    @Override
    public void invalidarCache() {
    }
}
