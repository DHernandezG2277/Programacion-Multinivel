package gestionrpg.repositorio;

import gestionrpg.modelo.Item;
import gestionrpg.modelo.Personaje;
import java.util.List;

/**
 * Contrato de la capa de persistencia: QUÉ operaciones ofrece cualquier
 * repositorio, no CÓMO se hacen. Si una clase que lo implementa olvida un
 * método, no compila.
 */
public interface PersonajeRepository {

    // >>> SALA 2.1 · El contrato
    // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
    // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
    // <<< FIN SALA 2.1

    /** Devuelve todos los ítems que existen en el juego (la tabla item). */
    List<Item> listarCatalogo();

    /**
     * Suma esa cantidad del ítem al inventario. Si ya lo tenía, se acumula.
     * false si el ítem no existe en el catálogo.
     */
    boolean agregarItem(int idPersonaje, String nombreItem, int cantidad);

    /** Saca el ítem completo del inventario. true si lo tenía. */
    boolean quitarItem(int idPersonaje, String nombreItem);
}
