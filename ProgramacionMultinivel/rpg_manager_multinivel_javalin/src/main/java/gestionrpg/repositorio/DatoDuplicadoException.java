package gestionrpg.repositorio;

/**
 * Ya existe un registro con ese valor único (por ejemplo, el nombre).
 * El repositorio solo avisa; el Service decide el mensaje para el usuario.
 */
public class DatoDuplicadoException extends RuntimeException {
    public DatoDuplicadoException(String valor, Throwable causa) {
        super("Valor duplicado: " + valor, causa);
    }
}
