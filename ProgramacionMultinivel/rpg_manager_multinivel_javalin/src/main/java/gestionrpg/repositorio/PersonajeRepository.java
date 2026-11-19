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
    /** Guarda un personaje nuevo, le asigna su id y lo devuelve. */
    Personaje guardar(Personaje p);

    /** Devuelve el personaje con ese id, o null si no existe. */
    Personaje buscarPorId(int id);

    /** Devuelve una lista NUEVA con todos los personajes. */
    List<Personaje> listarTodos();

    /** Devuelve una lista nueva con los personajes de esa clase. */
    List<Personaje> listarPorClase(String clase);

    /** Guarda los cambios de un personaje existente. true si existía. */
    boolean actualizar(Personaje p);

    /** Borra el personaje. true si existía. */
    boolean eliminar(int id);

    /** Devuelve los ítems del personaje (lista vacía si no tiene). */
    List<Item> listarInventario(int idPersonaje);

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
