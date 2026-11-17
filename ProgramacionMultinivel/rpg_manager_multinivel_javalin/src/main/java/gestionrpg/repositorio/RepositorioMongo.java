package gestionrpg.repositorio;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import gestionrpg.modelo.Arquero;
import gestionrpg.modelo.FabricaPersonajes;
import gestionrpg.modelo.Item;
import gestionrpg.modelo.Mago;
import gestionrpg.modelo.Personaje;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Implementación con MongoDB: la colección personajes cargada con el seed de la nivelación.
 * Un Document se usa como un Map: put/append para escribir, get para leer.
 *
 * Decisión sobre el id: los documentos del seed solo tienen _id (ObjectId) y el
 * Service trabaja con ids numéricos. Este repositorio agrega a cada documento un
 * campo numérico id_personaje, tomado de un contador en la colección
 * "contadores" (el equivalente al AUTO_INCREMENT). El Service nunca se entera.
 */
public class RepositorioMongo implements PersonajeRepository {

    private final MongoCollection<Document> personajes;
    private final MongoCollection<Document> contadores;
    private final MongoCollection<Document> items;      // catálogo: lo crea db/mongo_persistencia.js

    public RepositorioMongo(String uri, String base) {
        MongoClient cliente = MongoClients.create(uri);
        MongoDatabase db = cliente.getDatabase(base);
        this.personajes = db.getCollection("personajes");
        this.contadores = db.getCollection("contadores");
        this.items = db.getCollection("items");
        prepararIds();
    }

    /** Da un id_personaje numérico a los documentos que aún no lo tienen. */
    private void prepararIds() {
        if (contadores.find(eq("_id", "personajes")).first() == null) {
            Document mayor = personajes.find(exists("id_personaje"))
                    .sort(Sorts.descending("id_personaje")).first();
            int inicio = mayor != null ? numero(mayor.get("id_personaje"), 0) : 0;
            contadores.insertOne(new Document("_id", "personajes").append("seq", inicio));
        }
        // Primero se juntan los _id en una lista y luego se actualizan:
        // nunca se modifica la colección mientras se recorre el cursor
        List<Object> sinId = new ArrayList<>();
        for (Document doc : personajes.find(exists("id_personaje", false)).sort(Sorts.ascending("_id"))) {
            sinId.add(doc.get("_id"));
        }
        for (Object idMongo : sinId) {
            personajes.updateOne(eq("_id", idMongo), Updates.set("id_personaje", siguienteId()));
        }
        personajes.createIndex(Indexes.ascending("id_personaje"), new IndexOptions().unique(true));
        personajes.createIndex(Indexes.ascending("nombre"), new IndexOptions().unique(true));
    }

    private int siguienteId() {
        Document contador = contadores.findOneAndUpdate(eq("_id", "personajes"), Updates.inc("seq", 1),
                new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER));
        return numero(contador.get("seq"), 1);
    }

    /**
     * Según cómo se cargó el seed (mongosh, Compass, Atlas) un número puede llegar
     * como Integer o como Double: se lee como Number.
     */
    private static Integer numeroONull(Object valor) {
        return valor instanceof Number ? ((Number) valor).intValue() : null;
    }

    private static int numero(Object valor, int porDefecto) {
        Integer n = numeroONull(valor);
        return n != null ? n : porDefecto;
    }

    private static Personaje documentoAPersonaje(Document doc) {
        Object oro = doc.get("oro");
        BigDecimal oroDecimal = oro instanceof Number
                ? BigDecimal.valueOf(((Number) oro).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO.setScale(2);
        // get(): un Guerrero no tiene "mana" y un Mago no tiene "precision" -> null
        return FabricaPersonajes.crear(doc.getString("clase"), numero(doc.get("id_personaje"), 0),
                doc.getString("nombre"), numero(doc.get("nivel"), 1), numero(doc.get("vida"), 100),
                numero(doc.get("ataque"), 10), oroDecimal,
                numeroONull(doc.get("mana")), numeroONull(doc.get("precision")));
    }

    private static Document camposDe(Personaje p) {
        Document campos = new Document("nombre", p.getNombre())
                .append("clase", p.getClase())
                .append("nivel", p.getNivel())
                .append("vida", p.getVida())
                .append("ataque", p.getAtaque())
                .append("oro", p.getOro().doubleValue());   // el seed guarda oro como número
        if (p instanceof Mago) {
            campos.append("mana", ((Mago) p).getMana());
        }
        if (p instanceof Arquero) {
            campos.append("precision", ((Arquero) p).getPrecision());
        }
        return campos;
    }

    private List<Personaje> consultar(Bson filtro) {
        List<Personaje> resultado = new ArrayList<>();
        for (Document doc : personajes.find(filtro).sort(Sorts.ascending("id_personaje"))) {
            resultado.add(documentoAPersonaje(doc));
        }
        return resultado;
    }

    private static boolean esDuplicado(MongoWriteException e) {
        return e.getError().getCategory() == ErrorCategory.DUPLICATE_KEY;
    }

    @Override
    public Personaje guardar(Personaje p) {
        // >>> SALA 2.6 · MongoDB: guardar con contador
        // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
        // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        // <<< FIN SALA 2.6
        return p;
    }

    @Override
    public Personaje buscarPorId(int id) {
        Document doc = personajes.find(eq("id_personaje", id)).first();
        return doc != null ? documentoAPersonaje(doc) : null;
    }

    @Override
    public List<Personaje> listarTodos() {
        return consultar(new Document());
    }

    @Override
    public List<Personaje> listarPorClase(String clase) {
        return consultar(eq("clase", clase));
    }

    @Override
    public boolean actualizar(Personaje p) {
        // $set cambia solo esos campos: el inventario y las misiones embebidas se conservan
        try {
            return personajes.updateOne(eq("id_personaje", p.getId()), new Document("$set", camposDe(p)))
                    .getMatchedCount() > 0;
        } catch (MongoWriteException e) {
            if (esDuplicado(e)) {
                throw new DatoDuplicadoException(p.getNombre(), e);
            }
            throw e;
        }
    }

    @Override
    public boolean eliminar(int id) {
        // MongoDB no tiene llaves foráneas: no revisa si el personaje aparece en combates
        return personajes.deleteOne(eq("id_personaje", id)).getDeletedCount() > 0;
    }

    @Override
    public List<Item> listarInventario(int idPersonaje) {
        Document doc = personajes.find(eq("id_personaje", idPersonaje))
                .projection(Projections.include("inventario")).first();
        List<Item> items = new ArrayList<>();
        if (doc == null) {
            return items;
        }
        for (Document elemento : doc.getList("inventario", Document.class, new ArrayList<>())) {
            String tipo = elemento.getString("tipo");
            items.add(new Item(elemento.getString("item"), tipo != null ? tipo : "sin tipo",
                    numero(elemento.get("valor"), 0), numero(elemento.get("cantidad"), 1)));
        }
        return items;
    }

    @Override
    public List<Item> listarCatalogo() {
        List<Item> catalogo = new ArrayList<>();
        for (Document doc : items.find().sort(Sorts.ascending("tipo", "nombre"))) {
            catalogo.add(new Item(doc.getString("nombre"), doc.getString("tipo"),
                    numero(doc.get("valor"), 0), 1));
        }
        return catalogo;
    }

    @Override
    public boolean agregarItem(int idPersonaje, String nombreItem, int cantidad) {
        // 1. Si ya lo tiene: $inc sobre ese elemento del arreglo ($ = el que cumplió el filtro)
        // >>> SALA 2.8 · Inventario: agregar un ítem
        // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
        // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        // <<< FIN SALA 2.8
    }

    @Override
    public boolean quitarItem(int idPersonaje, String nombreItem) {
        // $pull saca del arreglo los elementos que cumplan la condición
        return personajes.updateOne(eq("id_personaje", idPersonaje),
                Updates.pull("inventario", new Document("item", nombreItem))).getModifiedCount() > 0;
    }
}
