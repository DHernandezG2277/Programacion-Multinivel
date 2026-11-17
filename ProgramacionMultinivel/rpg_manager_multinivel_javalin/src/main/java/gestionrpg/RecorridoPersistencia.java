package gestionrpg;

import gestionrpg.modelo.Item;
import gestionrpg.modelo.Personaje;
import gestionrpg.repositorio.RepositoryFactory;
import gestionrpg.servicio.PersonajeService;
import gestionrpg.servicio.ValidacionException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

/**
 * Reto 6: el mismo recorrido con cualquier motor.
 * En NetBeans: clic derecho sobre este archivo -> Run File.
 * Usa el motor de config.properties; un argumento (memoria | mariadb | mongo)
 * lo sobrescribe solo para esta ejecución. Copia la salida a salida_persistencia.txt.
 */
public class RecorridoPersistencia {

    private static final String NOMBRE_PRUEBA = "Kaelen";

    private static void imprimirLista(String titulo, List<Personaje> personajes) {
        System.out.println("\n" + titulo + " (" + personajes.size() + ")");
        for (Personaje p : personajes) {
            System.out.printf("  %3d  %-12s %-9s nivel %3d  vida %4d  oro %s%n",
                    p.getId(), p.getNombre(), p.getClase(), p.getNivel(), p.getVida(), p.getOro());
        }
    }

    public static void main(String[] args) {
        Properties cfg = Main.cargarConfiguracion();
        if (args.length > 0) {
            cfg.setProperty("repositorio", args[0]);
        }
        String motor = cfg.getProperty("repositorio");
        PersonajeService service = new PersonajeService(RepositoryFactory.crear(cfg));
        System.out.println("=== RPG Manager · recorrido de persistencia · Java · motor: " + motor + " ===");

        imprimirLista("1. Todos los personajes", service.listar());
        imprimirLista("2. Solo los Magos", service.listarPorClase("Mago"));

        List<Personaje> todos = service.listar();
        if (!todos.isEmpty()) {
            Personaje primero = todos.get(0);
            System.out.println("\n3. Inventario de " + primero.getNombre());
            List<Item> inventario = service.inventario(primero.getId());
            for (Item item : inventario) {
                System.out.printf("  %-18s %-9s valor %4d  x%d%n",
                        item.getNombre(), item.getTipo(), item.getValor(), item.getCantidad());
            }
            if (inventario.isEmpty()) {
                System.out.println("  (vacío)");
            }
        }

        // >>> SALA A.1 · Recorrido completo: Kaelen
        // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
        // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        // <<< FIN SALA A.1

        System.out.println("\n8. Validaciones del Service");
        String[][] intentos = { {"", "Mago", "100"}, {"Zed", "Paladin", "100"}, {"Zed", "Mago", "-5"} };
        for (String[] intento : intentos) {
            try {
                service.crear(intento[0], intento[1], 1, Integer.parseInt(intento[2]), 10, BigDecimal.ZERO);
            } catch (ValidacionException e) {
                System.out.println("  rechazado: " + e.getMessage());
            }
        }
    }
}
