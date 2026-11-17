package gestionrpg.repositorio;

/**
 * Error inesperado de la base de datos (conexión caída, SQL mal escrito...).
 * Envuelve SQLException, que es "checked": así el contrato no obliga a todo el
 * proyecto a declarar throws SQLException, y el Service no se entera de JDBC.
 */
public class AccesoDatosException extends RuntimeException {
    public AccesoDatosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
