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
        // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
        // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        // <<< FIN SALA 2.7
        }
    }
}
