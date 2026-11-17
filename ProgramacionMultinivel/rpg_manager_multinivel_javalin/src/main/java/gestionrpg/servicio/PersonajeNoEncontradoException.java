package gestionrpg.servicio;

/** No existe un personaje con ese id. */
public class PersonajeNoEncontradoException extends RuntimeException {
    public PersonajeNoEncontradoException(int id) {
        super("No existe el personaje " + id);
    }
}
