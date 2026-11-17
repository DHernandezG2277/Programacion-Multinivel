package gestionrpg.controlador;

import gestionrpg.modelo.Item;
import gestionrpg.modelo.Personaje;
import gestionrpg.servicio.PersonajeNoEncontradoException;
import gestionrpg.servicio.PersonajeService;
import gestionrpg.servicio.ValidacionException;
import gestionrpg.vista.PersonajeVista;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador: recibe la petición, llama al Service y elige la vista.
 * Aquí no hay SQL ni llamadas a MongoDB: si aparecen en una ruta, se rompió una capa.
 */
public class PersonajeControlador {

    private final PersonajeService service;
    private final String motor;    // solo para mostrarlo en la vista

    public PersonajeControlador(PersonajeService service, String motor) {
        this.service = service;
        this.motor = motor;
    }

    public void registrarRutas(Javalin app) {
        app.get("/", ctx -> ctx.redirect("/personajes"));
        app.get("/personajes", this::listar);
        app.get("/personajes/nuevo", this::formulario);
        app.post("/personajes", this::crear);
        app.get("/personajes/{id}", this::detalle);
        app.post("/personajes/{id}/vida", this::actualizarVida);
        app.post("/personajes/{id}/eliminar", this::eliminar);
        app.post("/personajes/{id}/inventario", this::agregarItem);
        app.post("/personajes/{id}/inventario/quitar", this::quitarItem);

        app.get("/api/personajes", this::apiListar);
        app.get("/api/personajes/{id}", this::apiObtener);
        app.get("/api/personajes/{id}/inventario", this::apiInventario);
        app.post("/api/personajes", this::apiCrear);

        // Errores comunes, en un solo lugar
        app.exception(PersonajeNoEncontradoException.class, (e, ctx) -> {
            ctx.status(404);
            if (ctx.path().startsWith("/api/")) {
                ctx.json(Map.of("error", e.getMessage()));
            } else {
                ctx.html(PersonajeVista.error(e.getMessage(), motor));
            }
        });
    }

    // ---------- lectura de parámetros (texto -> número) ----------

    private static int entero(String valor, String campo, int porDefecto) {
        if (valor == null || valor.isBlank()) {
            return porDefecto;
        }
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new ValidacionException("El campo " + campo + " debe ser un número entero");
        }
    }

    private static BigDecimal dinero(String valor) {
        if (valor == null || valor.isBlank()) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(valor.trim());
        } catch (NumberFormatException e) {
            throw new ValidacionException("El oro debe ser un número");
        }
    }

    private static int idDe(Context ctx) {
        try {
            return Integer.parseInt(ctx.pathParam("id"));
        } catch (NumberFormatException e) {
            throw new PersonajeNoEncontradoException(-1);
        }
    }

    private static String codificar(String texto) {
        return URLEncoder.encode(texto, StandardCharsets.UTF_8);
    }

    // ---------- vistas HTML ----------

    private void listar(Context ctx) {
        String clase = ctx.queryParam("clase");
        List<Personaje> personajes;
        String error = null;
        try {
            personajes = (clase == null || clase.isEmpty()) ? service.listar() : service.listarPorClase(clase);
        } catch (ValidacionException e) {
            personajes = service.listar();
            error = e.getMessage();
        }
        ctx.html(PersonajeVista.lista(personajes, clase, error, motor));
    }

    private void formulario(Context ctx) {
        ctx.html(PersonajeVista.formulario(new HashMap<>(), null, motor));
    }

    private void crear(Context ctx) {
        Map<String, String> datos = new HashMap<>();
        for (String campo : List.of("nombre", "clase", "nivel", "vida", "ataque", "oro")) {
            String valor = ctx.formParam(campo);
            if (valor != null) {
                datos.put(campo, valor);
            }
        }
        // >>> SALA 4.2 · La ruta POST crear
        // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
        // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        // <<< FIN SALA 4.2
    }

    private void detalle(Context ctx) {
        int id = idDe(ctx);
        Personaje p = service.obtener(id);
        List<Item> inventario = service.inventario(id);
        ctx.html(PersonajeVista.detalle(p, inventario, service.catalogo(), ctx.queryParam("error"), motor));
    }

    private void agregarItem(Context ctx) {
        // >>> SALA 4.3 · Las rutas del inventario
        // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
        // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        // <<< FIN SALA 4.3
    }

    private void quitarItem(Context ctx) {
        int id = idDe(ctx);
        try {
            service.quitarItem(id, ctx.formParam("item"));
        } catch (ValidacionException e) {
            ctx.redirect("/personajes/" + id + "?error=" + codificar(e.getMessage()));
            return;
        }
        ctx.redirect("/personajes/" + id);
    }

    private void actualizarVida(Context ctx) {
        int id = idDe(ctx);
        try {
            service.actualizarVida(id, entero(ctx.formParam("vida"), "vida", -1));
        } catch (ValidacionException e) {
            ctx.redirect("/personajes/" + id + "?error=" + codificar(e.getMessage()));
            return;
        }
        ctx.redirect("/personajes/" + id);
    }

    private void eliminar(Context ctx) {
        int id = idDe(ctx);
        try {
            service.eliminar(id);
        } catch (ValidacionException e) {
            ctx.redirect("/personajes/" + id + "?error=" + codificar(e.getMessage()));
            return;
        }
        ctx.redirect("/personajes");
    }

    // ---------- API JSON ----------

    private void apiListar(Context ctx) {
        String clase = ctx.queryParam("clase");
        try {
            ctx.json((clase == null || clase.isEmpty()) ? service.listar() : service.listarPorClase(clase));
        } catch (ValidacionException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        }
    }

    private void apiObtener(Context ctx) {
        ctx.json(service.obtener(idDe(ctx)));
    }

    private void apiInventario(Context ctx) {
        ctx.json(service.inventario(idDe(ctx)));
    }

    @SuppressWarnings("unchecked")
    private void apiCrear(Context ctx) {
        Map<String, Object> datos = ctx.bodyAsClass(Map.class);
        try {
            Personaje p = service.crear(
                    texto(datos.get("nombre")), texto(datos.get("clase")),
                    entero(texto(datos.get("nivel")), "nivel", 1), entero(texto(datos.get("vida")), "vida", 100),
                    entero(texto(datos.get("ataque")), "ataque", 10), dinero(texto(datos.get("oro"))));
            ctx.status(201).json(p);
        } catch (ValidacionException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        }
    }

    private static String texto(Object valor) {
        return valor == null ? null : valor.toString();
    }
}
