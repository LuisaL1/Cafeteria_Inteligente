package patrones.estructurales.proxy;

import models.Producto;

import java.util.List;

public interface MenuService {

    List<Producto> obtenerBebidas();
    List<Producto> obtenerComidas();
    List<Producto> obtenerPostres();
    void mostrarMenu();
    void invalidarCache();
}
