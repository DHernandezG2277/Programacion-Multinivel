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
        // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
        // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        // <<< FIN SALA 1.3
        }
    }
}
