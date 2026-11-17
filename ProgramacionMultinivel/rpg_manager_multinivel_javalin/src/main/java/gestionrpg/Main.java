package gestionrpg;

import gestionrpg.controlador.PersonajeControlador;
import gestionrpg.repositorio.PersonajeRepository;
import gestionrpg.repositorio.RepositoryFactory;
import gestionrpg.servicio.PersonajeService;
import io.javalin.Javalin;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Punto de arranque del RPG Manager (Javalin).
 *
 * 1. Lee config.properties (copia config.example.properties y pon tus datos).
 * 2. La fábrica crea el repositorio que diga la clave "repositorio".
 * 3. El Service recibe ese repositorio por el constructor.
 * 4. El controlador recibe el Service y registra las rutas.
 */
public class Main {

    public static Properties cargarConfiguracion() {
        Path ruta = Path.of("config.properties");    // raíz del proyecto en NetBeans
        if (!Files.exists(ruta)) {
            System.err.println("No existe config.properties en " + ruta.toAbsolutePath().getParent()
                    + ". Copia config.example.properties como config.properties y pon tus datos.");
            System.exit(1);
        }
        Properties cfg = new Properties();
        try (Reader lector = new InputStreamReader(Files.newInputStream(ruta), StandardCharsets.UTF_8)) {
            cfg.load(lector);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer config.properties", e);
        }
        return cfg;
    }

    public static void main(String[] args) {
        // >>> SALA 4.1 · El arranque arma la cadena
        // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
        // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        // <<< FIN SALA 4.1
        System.out.println("RPG Manager en http://localhost:7070/personajes · repositorio: "
                + cfg.getProperty("repositorio"));
    }
}
