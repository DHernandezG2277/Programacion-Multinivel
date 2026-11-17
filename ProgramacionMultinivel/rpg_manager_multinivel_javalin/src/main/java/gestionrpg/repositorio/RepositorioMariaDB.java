package gestionrpg.repositorio;

import gestionrpg.modelo.Arquero;
import gestionrpg.modelo.FabricaPersonajes;
import gestionrpg.modelo.Item;
import gestionrpg.modelo.Mago;
import gestionrpg.modelo.Personaje;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación con MariaDB: SQL escrito a mano (JDBC) sobre las tablas de la nivelación.
 * Tablas que usa: personaje, clase, inventario, item.
 * Requiere haber ejecutado db/alter_persistencia.sql (columnas mana y precision_tiro).
 */
public class RepositorioMariaDB implements PersonajeRepository {

    private static final int ERROR_DUPLICADO = 1062;    // violación de UNIQUE
    private static final int ERROR_FK_EN_USO = 1451;    // otra tabla apunta a esta fila

    // La clase NO está como texto en personaje: se trae de la tabla clase con un JOIN
    private static final String SELECT_PERSONAJE =
            "SELECT p.id_personaje, p.nombre, c.nombre AS clase, p.nivel, p.vida,"
            + " p.ataque, p.oro, p.mana, p.precision_tiro"
            + " FROM personaje p JOIN clase c ON p.id_clase = c.id_clase";

    private final String url;
    private final String usuario;
    private final String clave;
    private final Map<String, Integer> idClase = new HashMap<>();   // "Mago" -> 2

    public RepositorioMariaDB(String host, String puerto, String base, String usuario, String clave) {
        this.url = "jdbc:mariadb://" + host + ":" + puerto + "/" + base;
        this.usuario = usuario;
        this.clave = clave;
        // Mapa nombre de clase -> id_clase, cargado una sola vez al arrancar
        try (Connection con = conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id_clase, nombre FROM clase")) {
            while (rs.next()) {
                idClase.put(rs.getString("nombre"), rs.getInt("id_clase"));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo conectar a MariaDB en " + url, e);
        }
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, usuario, clave);
    }

    private static Personaje filaAPersonaje(ResultSet rs) throws SQLException {
        // getObject devuelve null si la columna es NULL (getInt devolvería 0)
        Integer mana = rs.getObject("mana", Integer.class);
        Integer precision = rs.getObject("precision_tiro", Integer.class);
        return FabricaPersonajes.crear(rs.getString("clase"), rs.getInt("id_personaje"),
                rs.getString("nombre"), rs.getInt("nivel"), rs.getInt("vida"), rs.getInt("ataque"),
                rs.getBigDecimal("oro"), mana, precision);
    }

    private List<Personaje> consultar(String sql, Object... parametros) {
        List<Personaje> resultado = new ArrayList<>();
        // >>> SALA 2.3 · MariaDB: leer un personaje
        // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
        // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        // <<< FIN SALA 2.3
    }

    /** Pone en el PreparedStatement los campos que comparten INSERT y UPDATE (posiciones 1 a 8). */
    private void ponerCampos(PreparedStatement ps, Personaje p) throws SQLException {
        ps.setString(1, p.getNombre());
        ps.setInt(2, idClase.get(p.getClase()));
        ps.setInt(3, p.getNivel());
        ps.setInt(4, p.getVida());
        ps.setInt(5, p.getAtaque());
        ps.setBigDecimal(6, p.getOro());
        if (p instanceof Mago) {
            ps.setInt(7, ((Mago) p).getMana());
        } else {
            ps.setNull(7, Types.INTEGER);
        }
        if (p instanceof Arquero) {
            ps.setInt(8, ((Arquero) p).getPrecision());
        } else {
            ps.setNull(8, Types.INTEGER);
        }
    }

    @Override
    public Personaje guardar(Personaje p) {
        String sql = "INSERT INTO personaje (nombre, id_clase, nivel, vida, ataque, oro, mana, precision_tiro)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // >>> SALA 2.4 · MariaDB: guardar con INSERT
            // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
            // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
            // <<< FIN SALA 2.4
        }
    }

    @Override
    public Personaje buscarPorId(int id) {
        List<Personaje> encontrados = consultar(SELECT_PERSONAJE + " WHERE p.id_personaje = ?", id);
        return encontrados.isEmpty() ? null : encontrados.get(0);
    }

    @Override
    public List<Personaje> listarTodos() {
        return consultar(SELECT_PERSONAJE + " ORDER BY p.id_personaje");
    }

    @Override
    public List<Personaje> listarPorClase(String clase) {
        return consultar(SELECT_PERSONAJE + " WHERE c.nombre = ? ORDER BY p.id_personaje", clase);
    }

    @Override
    public boolean actualizar(Personaje p) {
        String sql = "UPDATE personaje SET nombre = ?, id_clase = ?, nivel = ?, vida = ?, ataque = ?,"
                + " oro = ?, mana = ?, precision_tiro = ? WHERE id_personaje = ?";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ponerCampos(ps, p);
            ps.setInt(9, p.getId());
            int filas = ps.executeUpdate();
            // MariaDB cuenta 0 filas si el UPDATE no cambió nada; por eso se confirma que exista
            return filas > 0 || buscarPorId(p.getId()) != null;
        } catch (SQLException e) {
            if (e.getErrorCode() == ERROR_DUPLICADO) {
                throw new DatoDuplicadoException(p.getNombre(), e);
            }
            throw new AccesoDatosException("Error actualizando personaje", e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        // >>> SALA 2.5 · MariaDB: eliminar y el error 1451
        // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
        // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        // <<< FIN SALA 2.5
        }
    }

    @Override
    public List<Item> listarInventario(int idPersonaje) {
        String sql = "SELECT i.nombre, i.tipo, i.valor, inv.cantidad"
                + " FROM inventario inv JOIN item i ON inv.id_item = i.id_item"
                + " WHERE inv.id_personaje = ? ORDER BY i.nombre";
        List<Item> items = new ArrayList<>();
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPersonaje);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(new Item(rs.getString("nombre"), rs.getString("tipo"),
                            rs.getInt("valor"), rs.getInt("cantidad")));
                }
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error consultando inventario", e);
        }
        return items;
    }

    @Override
    public List<Item> listarCatalogo() {
        List<Item> items = new ArrayList<>();
        try (Connection con = conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT nombre, tipo, valor FROM item ORDER BY tipo, nombre")) {
            while (rs.next()) {
                items.add(new Item(rs.getString("nombre"), rs.getString("tipo"), rs.getInt("valor"), 1));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error consultando el catálogo de ítems", e);
        }
        return items;
    }

    @Override
    public boolean agregarItem(int idPersonaje, String nombreItem, int cantidad) {
        // El id_item sale de la tabla item por su nombre. Si el personaje ya tenía
        // el ítem, la llave primaria (id_personaje, id_item) se repite y
        // ON DUPLICATE KEY UPDATE suma la cantidad en vez de fallar.
        String sql = "INSERT INTO inventario (id_personaje, id_item, cantidad)"
                + " SELECT ?, id_item, ? FROM item WHERE nombre = ?"
                + " ON DUPLICATE KEY UPDATE cantidad = inventario.cantidad + VALUES(cantidad)";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPersonaje);
            ps.setInt(2, cantidad);
            ps.setString(3, nombreItem);
            return ps.executeUpdate() > 0;      // 0 filas: el ítem no existe en la tabla item
        } catch (SQLException e) {
            throw new AccesoDatosException("Error agregando ítem al inventario", e);
        }
    }

    @Override
    public boolean quitarItem(int idPersonaje, String nombreItem) {
        String sql = "DELETE inv FROM inventario inv JOIN item i ON inv.id_item = i.id_item"
                + " WHERE inv.id_personaje = ? AND i.nombre = ?";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPersonaje);
            ps.setString(2, nombreItem);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AccesoDatosException("Error quitando ítem del inventario", e);
        }
    }
}
