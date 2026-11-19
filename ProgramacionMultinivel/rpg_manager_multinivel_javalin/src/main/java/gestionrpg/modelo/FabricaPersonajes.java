package gestionrpg.modelo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Reconstruye la subclase correcta a partir del texto de la clase.
 * La usan los tres repositorios: una fila de MariaDB, un documento de MongoDB
 * o un formulario dicen "Mago", y aquí se decide qué objeto crear.
 */
public final class FabricaPersonajes {

    public static final List<String> CLASES_VALIDAS = List.of("Guerrero", "Mago", "Arquero");

    private FabricaPersonajes() { }

    /**
     * @param mana      solo para Mago; null = valor por defecto
     * @param precision solo para Arquero; null = valor por defecto
     */
    public static Personaje crear(String clase, Integer id, String nombre, int nivel, int vida,
                                  int ataque, BigDecimal oro, Integer mana, Integer precision) {
        // >>> SALA 1.3 · La fábrica de personajes
        switch (clase) {
            case "Guerrero":
                return new Guerrero(id, nombre, nivel, vida, ataque, oro);
            case "Mago":
                return new Mago(id, nombre, nivel, vida, ataque, oro,
                        mana != null ? mana : Mago.MANA_POR_DEFECTO);
            case "Arquero":
                return new Arquero(id, nombre, nivel, vida, ataque, oro,
                        precision != null ? precision : Arquero.PRECISION_POR_DEFECTO);
            default:
                throw new IllegalArgumentException("Clase desconocida: " + clase);

        // <<< FIN SALA 1.3
        }
    }
}
