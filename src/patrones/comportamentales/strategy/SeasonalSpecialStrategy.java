package patrones.comportamentales.strategy;

import models.Producto;

import java.util.List;

public interface SeasonalSpecialStrategy {

    List<Producto> obtenerPlatosEspeciales();
    String getNombreTemporada();
    void mostrarMenuEspecial();
}
