package gestionrpg.servicio;

import gestionrpg.modelo.FabricaPersonajes;
import gestionrpg.modelo.Item;
import gestionrpg.modelo.Personaje;
import gestionrpg.repositorio.DatoDuplicadoException;
import gestionrpg.repositorio.DatoEnUsoException;
import gestionrpg.repositorio.PersonajeRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Capa de negocio: las reglas del juego.
 * Recibe el repositorio por el constructor y solo conoce el contrato
 * PersonajeRepository: no sabe si detrás hay memoria, MariaDB o MongoDB.
 */
// >>> SALA 3.1 · Inyección por constructor
// Arma este bloque en el Edificio de la Persistencia (pestaña Java),
// pulsa «Copiar bloque» y pégalo entre estas dos marcas.
// <<< FIN SALA 3.1
    }

    public List<Personaje> listarPorClase(String clase) {
        validarClase(clase);
        return repositorio.listarPorClase(clase);
    }

    // >>> SALA 3.2 · Obtener: de null a 404
    // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
    // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
    // <<< FIN SALA 3.2
    }

    public List<Item> catalogo() {
        return repositorio.listarCatalogo();
    }

    // ---------- inventario ----------

    public List<Item> agregarItem(int id, String nombreItem, int cantidad) {
        // >>> SALA 3.4 · Agregar ítem: reglas del negocio
        // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
        // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        // <<< FIN SALA 3.4
    }

    public void quitarItem(int id, String nombreItem) {
        Personaje p = obtener(id);
        if (!repositorio.quitarItem(id, nombreItem)) {
            throw new ValidacionException(p.getNombre() + " no tiene " + nombreItem + " en su inventario");
        }
    }

    // ---------- cambios ----------

    public Personaje crear(String nombre, String clase, int nivel, int vida, int ataque, BigDecimal oro) {
        nombre = nombre == null ? "" : nombre.trim();
        if (nombre.isEmpty()) {
            throw new ValidacionException("El nombre es obligatorio");
        }
        if (nombre.length() > 50) {
            throw new ValidacionException("El nombre admite máximo 50 caracteres");
        }
        validarClase(clase);
        if (nivel < 1 || nivel > 100) {
            throw new ValidacionException("El nivel debe estar entre 1 y 100");
        }
        if (vida < 0) {
            throw new ValidacionException("La vida no puede ser negativa");
        }
        if (ataque < 0) {
            throw new ValidacionException("El ataque no puede ser negativo");
        }
        if (oro == null || oro.signum() < 0) {
            throw new ValidacionException("El oro no puede ser negativo");
        }
        // >>> SALA 3.3 · Crear: validar y traducir
        // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
        // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        // <<< FIN SALA 3.3
        }
    }

    public Personaje actualizarVida(int id, int vida) {
        if (vida < 0) {
            throw new ValidacionException("La vida no puede ser negativa");
        }
        Personaje p = obtener(id);
        p.setVida(vida);
        repositorio.actualizar(p);
        return p;
    }

    public void eliminar(int id) {
        obtener(id);
        try {
            repositorio.eliminar(id);
        } catch (DatoEnUsoException e) {
            throw new ValidacionException("No se puede eliminar: el personaje tiene combates registrados");
        }
    }

    // ---------- validaciones auxiliares ----------

    private static void validarClase(String clase) {
        if (clase == null || !FabricaPersonajes.CLASES_VALIDAS.contains(clase)) {
            throw new ValidacionException("Clase inválida: " + clase + ". Usa Guerrero, Mago o Arquero");
        }
    }
}
