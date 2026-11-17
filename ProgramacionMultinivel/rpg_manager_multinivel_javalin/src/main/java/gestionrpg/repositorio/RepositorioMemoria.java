package gestionrpg.repositorio;

import gestionrpg.modelo.Item;
import gestionrpg.modelo.Personaje;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación en memoria: un HashMap cuya clave es el id.
 * Sirve para probar sin base de datos. Al reiniciar el servidor, todo se pierde.
 */
public class RepositorioMemoria implements PersonajeRepository {

    /** Catálogo fijo: los mismos 9 ítems de la tabla item de MariaDB. */
    private static final List<Item> CATALOGO = List.of(
            new Item("Espada larga", "arma", 120, 1),
            new Item("Hacha de guerra", "arma", 200, 1),
            new Item("Báculo arcano", "arma", 250, 1),
            new Item("Arco élfico", "arma", 180, 1),
            new Item("Cota de malla", "armadura", 300, 1),
            new Item("Túnica encantada", "armadura", 220, 1),
            new Item("Poción de vida", "pocion", 25, 1),
            new Item("Elixir de maná", "pocion", 40, 1),
            new Item("Amuleto del dragón", "reliquia", 1000, 1));

    private final Map<Integer, Personaje> datos = new HashMap<>();
    private final Map<Integer, List<Item>> inventarios = new HashMap<>();
    private int siguienteId = 1;    // como AUTO_INCREMENT: nunca reutiliza un id

    @Override
    public Personaje guardar(Personaje p) {
        // Misma regla que el UNIQUE de MariaDB sobre el nombre
        // >>> SALA 2.2 · RepositorioMemoria
        // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
        // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        // <<< FIN SALA 2.2
    }

    @Override
    public Personaje buscarPorId(int id) {
        return datos.get(id);    // null si no existe
    }

    @Override
    public List<Personaje> listarTodos() {
        return new ArrayList<>(datos.values());   // copia: quien la reciba no puede dañar el mapa
    }

    @Override
    public List<Personaje> listarPorClase(String clase) {
        List<Personaje> resultado = new ArrayList<>();
        for (Personaje p : datos.values()) {
            if (p.getClase().equals(clase)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    @Override
    public boolean actualizar(Personaje p) {
        if (p.getId() == null || !datos.containsKey(p.getId())) {
            return false;
        }
        datos.put(p.getId(), p);
        return true;
    }

    @Override
    public boolean eliminar(int id) {
        // remove por clave: nada de borrar dentro de un for-each
        inventarios.remove(id);
        return datos.remove(id) != null;
    }

    @Override
    public List<Item> listarInventario(int idPersonaje) {
        return new ArrayList<>(inventarios.getOrDefault(idPersonaje, List.of()));
    }

    @Override
    public List<Item> listarCatalogo() {
        return new ArrayList<>(CATALOGO);
    }

    @Override
    public boolean agregarItem(int idPersonaje, String nombreItem, int cantidad) {
        Item delCatalogo = null;
        for (Item item : CATALOGO) {
            if (item.getNombre().equals(nombreItem)) {
                delCatalogo = item;
            }
        }
        if (delCatalogo == null) {
            return false;
        }
        List<Item> inventario = inventarios.computeIfAbsent(idPersonaje, k -> new ArrayList<>());
        for (int i = 0; i < inventario.size(); i++) {
            Item item = inventario.get(i);
            if (item.getNombre().equals(nombreItem)) {
                // Item no tiene setters: se reemplaza por uno con la cantidad sumada
                inventario.set(i, new Item(item.getNombre(), item.getTipo(), item.getValor(),
                        item.getCantidad() + cantidad));
                return true;
            }
        }
        inventario.add(new Item(delCatalogo.getNombre(), delCatalogo.getTipo(), delCatalogo.getValor(), cantidad));
        return true;
    }

    @Override
    public boolean quitarItem(int idPersonaje, String nombreItem) {
        List<Item> inventario = inventarios.getOrDefault(idPersonaje, new ArrayList<>());
        // removeIf en vez de borrar dentro de un for-each (ConcurrentModificationException)
        return inventario.removeIf(item -> item.getNombre().equals(nombreItem));
    }
}
