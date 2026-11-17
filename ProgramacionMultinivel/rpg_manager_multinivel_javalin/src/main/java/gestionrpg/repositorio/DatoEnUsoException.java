package gestionrpg.repositorio;

/** El registro no se puede borrar porque otros datos lo referencian (llave foránea). */
public class DatoEnUsoException extends RuntimeException {
    public DatoEnUsoException(int id, Throwable causa) {
        super("Registro en uso: " + id, causa);
    }
}
