package gestionrpg.servicio;

/** Un dato no cumple una regla del juego. El mensaje es para el usuario. */
public class ValidacionException extends RuntimeException {
    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}
