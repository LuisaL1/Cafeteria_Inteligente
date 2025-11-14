package patrones.comportamentales.strategy.seasonalmenuContext;

import models.Bebida;
import models.Comida;
import models.Postre;
import models.Producto;
import patrones.comportamentales.strategy.seasonalmenuContext.SeasonalSpecialStrategy;

import java.util.ArrayList;
import java.util.List;

public class ChristmasSpecialStrategy implements SeasonalSpecialStrategy {

    private List<Producto> platosEspeciales;

    public ChristmasSpecialStrategy() {
        inicializarPlatosNavidad();
    }

    private void inicializarPlatosNavidad() {
        platosEspeciales = new ArrayList<>();

        // Bebidas temáticas de Navidad
        platosEspeciales.add(new Bebida("Café con Especias Navideñas", 8.50, "Grande"));
        platosEspeciales.add(new Bebida("Chocolate Caliente Navideño", 8.00, "Grande"));
        platosEspeciales.add(new Bebida("Latte de Jengibre", 8.20, "Mediano"));
        platosEspeciales.add(new Bebida("Ponche de Frutas", 7.00, "Grande"));

        // Comidas temáticas de Navidad
        platosEspeciales.add(new Comida("Pavo en Croissant", 11.00, false));
        platosEspeciales.add(new Comida("Sandwich Festivo", 10.50, false));
        platosEspeciales.add(new Comida("Ensalada Navideña", 9.00, true));

        // Postres temáticos de Navidad
        platosEspeciales.add(new Postre("Tronco de Navidad", 8.50, false));
        platosEspeciales.add(new Postre("Galletas de Jengibre", 6.00, false));
        platosEspeciales.add(new Postre("Cheesecake de Frutos Rojos", 8.00, false));
        platosEspeciales.add(new Postre("Pan de Pascua", 7.50, false));
    }

    @Override
    public List<Producto> obtenerPlatosEspeciales() {
        return new ArrayList<>(platosEspeciales); // Retorna copia para evitar modificaciones
    }

    @Override
    public String getNombreTemporada() {
        return "Navidad";
    }

    @Override
    public void mostrarMenuEspecial() {
        System.out.println("\n🎄========================================🎄");
        System.out.println("        🎅 MENÚ ESPECIAL DE NAVIDAD 🎅");
        System.out.println("🎄========================================🎄");
        System.out.println("   ¡Sabores festivos para celebrar!");
        System.out.println("------------------------------------------");

        System.out.println("\n☕ BEBIDAS FESTIVAS:");
        platosEspeciales.stream()
                .filter(p -> p.getCategoria().equals("Bebida"))
                .forEach(p -> System.out.println("  ⛄ " + p));

        System.out.println("\n🍴 COMIDAS NAVIDEÑAS:");
        platosEspeciales.stream()
                .filter(p -> p.getCategoria().equals("Comida"))
                .forEach(p -> System.out.println("  🎁 " + p));

        System.out.println("\n🍰 POSTRES DE TEMPORADA:");
        platosEspeciales.stream()
                .filter(p -> p.getCategoria().equals("Postre"))
                .forEach(p -> System.out.println("  🔔 " + p));

        System.out.println("\n🎄========================================🎄\n");
    }
}
