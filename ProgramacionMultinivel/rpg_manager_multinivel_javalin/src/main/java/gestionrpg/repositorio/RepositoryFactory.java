package gestionrpg.repositorio;

import java.util.Properties;

/**
 * Selector: lee la configuración y crea la implementación del contrato.
 * Es el único lugar del proyecto que sabe qué motor hay detrás.
 */
public final class RepositoryFactory {

    private RepositoryFactory() { }

    public static PersonajeRepository crear(Properties cfg) {
        // >>> SALA 2.7 · El selector: fábrica y configuración
        String tipo = cfg.getProperty("repositorio", "").trim().toLowerCase();
        switch (tipo) {
            case "memoria":
                return new RepositorioMemoria();
            case "mariadb":
                return new RepositorioMariaDB(
                        cfg.getProperty("mariadb.host"),
                        cfg.getProperty("mariadb.puerto"),
                        cfg.getProperty("mariadb.base"),
                        cfg.getProperty("mariadb.usuario"),
                        cfg.getProperty("mariadb.clave"));
            case "mongo":
                return new RepositorioMongo(cfg.getProperty("mongo.uri"), cfg.getProperty("mongo.base"));
            default:
                // Mejor que la app no arranque a que falle en la primera petición
                throw new IllegalStateException(
                        "Repositorio desconocido: '" + tipo + "'. Usa memoria, mariadb o mongo.");

        // <<< FIN SALA 2.7
        }
    }
}
