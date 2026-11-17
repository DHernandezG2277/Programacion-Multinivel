package gestionrpg.vista;

import gestionrpg.modelo.Arquero;
import gestionrpg.modelo.FabricaPersonajes;
import gestionrpg.modelo.Item;
import gestionrpg.modelo.Mago;
import gestionrpg.modelo.Personaje;
import java.util.List;
import java.util.Map;

/**
 * Vista: arma el HTML con Bootstrap 5 (desde CDN). No decide nada ni consulta datos:
 * solo muestra lo que le pasa el controlador. Todo texto que viene del usuario pasa por escapar().
 * Las páginas se ven igual que las plantillas de Flask.
 */
public final class PersonajeVista {

    private static final String BOOTSTRAP_CSS =
            "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css";
    private static final String BOOTSTRAP_JS =
            "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js";

    private PersonajeVista() { }

    private static String escapar(Object valor) {
        // >>> SALA 4.4 · La vista con Bootstrap
        // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
        // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        // <<< FIN SALA 4.4
    }

    private static String pagina(String titulo, String motor, String cuerpo) {
        return "<!doctype html><html lang=\"es\"><head><meta charset=\"utf-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
                + "<title>RPG Manager · " + escapar(titulo) + "</title>"
                + "<link href=\"" + BOOTSTRAP_CSS + "\" rel=\"stylesheet\">"
                + "</head><body class=\"bg-light\">"
                + "<nav class=\"navbar navbar-dark bg-dark mb-4\"><div class=\"container\">"
                + "<a class=\"navbar-brand fw-bold\" href=\"/personajes\">RPG Manager</a>"
                + "<span class=\"badge rounded-pill text-bg-light\">Repositorio: " + escapar(motor) + "</span>"
                + "</div></nav>"
                + "<main class=\"container pb-5\">" + cuerpo + "</main>"
                + "<script src=\"" + BOOTSTRAP_JS + "\"></script>"
                + "</body></html>";
    }

    private static String alerta(String error) {
        return error == null || error.isEmpty() ? ""
                : "<div class=\"alert alert-danger\" role=\"alert\">" + escapar(error) + "</div>";
    }

    /** Color del badge según la clase del personaje. */
    private static String badgeClase(String clase) {
        String color;
        switch (clase) {
            case "Guerrero": color = "danger"; break;
            case "Mago": color = "primary"; break;
            case "Arquero": color = "success"; break;
            default: color = "secondary";
        }
        return "<span class=\"badge text-bg-" + color + "\">" + escapar(clase) + "</span>";
    }

    private static String opcionesClase(String seleccionada, boolean incluirTodas) {
        StringBuilder html = new StringBuilder();
        if (incluirTodas) {
            html.append("<option value=\"\">Todas</option>");
        }
        for (String c : FabricaPersonajes.CLASES_VALIDAS) {
            html.append("<option value=\"").append(c).append("\"")
                .append(c.equals(seleccionada) ? " selected" : "")
                .append(">").append(c).append("</option>");
        }
        return html.toString();
    }

    private static String campoNumero(String id, String etiqueta, String valor, String extra) {
        return "<div class=\"col-6\"><label class=\"form-label\" for=\"" + id + "\">" + etiqueta + "</label>"
                + "<input class=\"form-control\" id=\"" + id + "\" name=\"" + id + "\" type=\"number\"" + extra
                + " value=\"" + escapar(valor) + "\"></div>";
    }

    private static String dato(String etiqueta, Object valor) {
        return "<div class=\"col\"><div class=\"border rounded p-2 text-center\">"
                + "<div class=\"small text-muted\">" + etiqueta + "</div>"
                + "<div class=\"fs-5 fw-semibold\">" + escapar(valor) + "</div></div></div>";
    }

    public static String lista(List<Personaje> personajes, String claseFiltro, String error, String motor) {
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"d-flex flex-wrap justify-content-between align-items-center gap-2 mb-3\">")
            .append("<h1 class=\"h3 mb-0\">Personajes</h1>")
            .append("<a class=\"btn btn-success\" href=\"/personajes/nuevo\">+ Nuevo personaje</a></div>")
            .append("<form class=\"row g-2 align-items-end mb-3\" method=\"get\" action=\"/personajes\">")
            .append("<div class=\"col-auto\"><label class=\"form-label mb-1\" for=\"clase\">Filtrar por clase</label>")
            .append("<select class=\"form-select\" id=\"clase\" name=\"clase\">")
            .append(opcionesClase(claseFiltro, true))
            .append("</select></div><div class=\"col-auto\">")
            .append("<button class=\"btn btn-outline-primary\" type=\"submit\">Filtrar</button></div></form>")
            .append(alerta(error))
            .append("<div class=\"card shadow-sm\"><div class=\"table-responsive\">")
            .append("<table class=\"table table-hover align-middle mb-0\"><thead class=\"table-dark\">")
            .append("<tr><th>Id</th><th>Nombre</th><th>Clase</th><th class=\"text-end\">Nivel</th>")
            .append("<th class=\"text-end\">Vida</th><th class=\"text-end\">Ataque</th><th class=\"text-end\">Oro</th></tr>")
            .append("</thead><tbody>");
        for (Personaje p : personajes) {
            html.append("<tr><td>").append(p.getId()).append("</td>")
                .append("<td><a class=\"fw-semibold text-decoration-none\" href=\"/personajes/").append(p.getId()).append("\">")
                .append(escapar(p.getNombre())).append("</a></td>")
                .append("<td>").append(badgeClase(p.getClase())).append("</td>")
                .append("<td class=\"text-end\">").append(p.getNivel()).append("</td>")
                .append("<td class=\"text-end\">").append(p.getVida()).append("</td>")
                .append("<td class=\"text-end\">").append(p.getAtaque()).append("</td>")
                .append("<td class=\"text-end\">").append(p.getOro()).append("</td></tr>");
        }
        if (personajes.isEmpty()) {
            html.append("<tr><td colspan=\"7\" class=\"text-center text-muted py-4\">No hay personajes.</td></tr>");
        }
        html.append("</tbody></table></div></div>");
        return pagina("Personajes", motor, html.toString());
    }

    public static String formulario(Map<String, String> datos, String error, String motor) {
        String cuerpo = "<div class=\"row justify-content-center\"><div class=\"col-lg-6\">"
                + "<div class=\"card shadow-sm\"><div class=\"card-body\">"
                + "<h1 class=\"h4 card-title mb-3\">Nuevo personaje</h1>" + alerta(error)
                + "<form method=\"post\" action=\"/personajes\">"
                + "<div class=\"mb-3\"><label class=\"form-label\" for=\"nombre\">Nombre</label>"
                + "<input class=\"form-control\" id=\"nombre\" name=\"nombre\" required value=\""
                + escapar(datos.getOrDefault("nombre", "")) + "\"></div>"
                + "<div class=\"mb-3\"><label class=\"form-label\" for=\"clase\">Clase</label>"
                + "<select class=\"form-select\" id=\"clase\" name=\"clase\">"
                + opcionesClase(datos.get("clase"), false) + "</select></div>"
                + "<div class=\"row g-3 mb-3\">"
                + campoNumero("nivel", "Nivel", datos.getOrDefault("nivel", "1"), "")
                + campoNumero("vida", "Vida", datos.getOrDefault("vida", "100"), "")
                + campoNumero("ataque", "Ataque", datos.getOrDefault("ataque", "10"), "")
                + campoNumero("oro", "Oro", datos.getOrDefault("oro", "0"), " step=\"0.01\"")
                + "</div><div class=\"d-flex gap-2\">"
                + "<button class=\"btn btn-success\" type=\"submit\">Crear</button>"
                + "<a class=\"btn btn-outline-secondary\" href=\"/personajes\">Cancelar</a>"
                + "</div></form></div></div></div></div>";
        return pagina("Nuevo personaje", motor, cuerpo);
    }

    public static String detalle(Personaje p, List<Item> inventario, List<Item> catalogo,
                                 String error, String motor) {
        StringBuilder html = new StringBuilder();
        html.append("<a class=\"btn btn-link px-0 mb-2\" href=\"/personajes\">&larr; Volver a la lista</a>")
            .append(alerta(error))
            .append("<div class=\"card shadow-sm mb-4\"><div class=\"card-body\">")
            .append("<h1 class=\"h3 card-title\">").append(escapar(p.getNombre())).append(" ")
            .append(badgeClase(p.getClase())).append("</h1>")
            .append("<div class=\"row row-cols-2 row-cols-md-4 g-3 mt-1\">")
            .append(dato("Nivel", p.getNivel()))
            .append(dato("Vida", p.getVida()))
            .append(dato("Ataque", p.getAtaque()))
            .append(dato("Oro", p.getOro()));
        if (p instanceof Mago) {
            html.append(dato("Mana", ((Mago) p).getMana()));
        }
        if (p instanceof Arquero) {
            html.append(dato("Precisión", ((Arquero) p).getPrecision()));
        }
        html.append("</div></div></div>")
            .append("<h2 class=\"h5\">Inventario</h2>")
            .append("<div class=\"card shadow-sm mb-4\"><div class=\"table-responsive\">")
            .append("<table class=\"table table-striped align-middle mb-0\"><thead class=\"table-dark\">")
            .append("<tr><th>Ítem</th><th>Tipo</th><th class=\"text-end\">Valor</th><th class=\"text-end\">Cantidad</th><th></th></tr>")
            .append("</thead><tbody>");
        for (Item item : inventario) {
            html.append("<tr><td>").append(escapar(item.getNombre())).append("</td><td>")
                .append(escapar(item.getTipo())).append("</td><td class=\"text-end\">").append(item.getValor())
                .append("</td><td class=\"text-end\">").append(item.getCantidad()).append("</td>")
                .append("<td class=\"text-end\"><form method=\"post\" action=\"/personajes/").append(p.getId())
                .append("/inventario/quitar\"><input type=\"hidden\" name=\"item\" value=\"")
                .append(escapar(item.getNombre())).append("\">")
                .append("<button class=\"btn btn-sm btn-outline-danger\" type=\"submit\">Quitar</button>")
                .append("</form></td></tr>");
        }
        if (inventario.isEmpty()) {
            html.append("<tr><td colspan=\"5\" class=\"text-center text-muted py-3\">Inventario vacío.</td></tr>");
        }
        html.append("</tbody></table></div>")
            .append("<div class=\"card-footer bg-white\">").append(formularioItem(p, catalogo))
            .append("</div></div>")
            .append("<div class=\"row g-3\">")
            .append("<div class=\"col-md-6\"><div class=\"card shadow-sm h-100\"><div class=\"card-body\">")
            .append("<h2 class=\"h5 card-title\">Cambiar vida</h2>")
            .append("<form class=\"d-flex gap-2\" method=\"post\" action=\"/personajes/").append(p.getId()).append("/vida\">")
            .append("<input class=\"form-control\" name=\"vida\" type=\"number\" value=\"").append(p.getVida()).append("\">")
            .append("<button class=\"btn btn-primary\" type=\"submit\">Guardar</button></form>")
            .append("</div></div></div>")
            .append("<div class=\"col-md-6\"><div class=\"card shadow-sm h-100 border-danger\"><div class=\"card-body\">")
            .append("<h2 class=\"h5 card-title text-danger\">Eliminar personaje</h2>")
            .append("<form method=\"post\" action=\"/personajes/").append(p.getId()).append("/eliminar\"")
            .append(" onsubmit=\"return confirm('¿Eliminar este personaje? Esta acción no se puede deshacer.');\">")
            .append("<button class=\"btn btn-outline-danger\" type=\"submit\">Eliminar</button></form>")
            .append("</div></div></div></div>");
        return pagina(p.getNombre(), motor, html.toString());
    }

    /** Formulario para agregar un ítem del catálogo al inventario. */
    private static String formularioItem(Personaje p, List<Item> catalogo) {
        if (catalogo.isEmpty()) {
            return "<p class=\"text-muted mb-0\">No hay ítems en el catálogo."
                    + " En MongoDB, ejecuta db/mongo_persistencia.js.</p>";
        }
        StringBuilder opciones = new StringBuilder();
        for (Item c : catalogo) {
            opciones.append("<option value=\"").append(escapar(c.getNombre())).append("\">")
                    .append(escapar(c.getNombre())).append(" · ").append(escapar(c.getTipo()))
                    .append(" · ").append(c.getValor()).append(" de valor</option>");
        }
        return "<form class=\"row g-2 align-items-end\" method=\"post\" action=\"/personajes/" + p.getId() + "/inventario\">"
                + "<div class=\"col-sm-7\"><label class=\"form-label mb-1\" for=\"item\">Agregar ítem</label>"
                + "<select class=\"form-select\" id=\"item\" name=\"item\">" + opciones + "</select></div>"
                + "<div class=\"col-6 col-sm-2\"><label class=\"form-label mb-1\" for=\"cantidad\">Cantidad</label>"
                + "<input class=\"form-control\" id=\"cantidad\" name=\"cantidad\" type=\"number\" min=\"1\" max=\"99\" value=\"1\"></div>"
                + "<div class=\"col-6 col-sm-3 d-grid\"><button class=\"btn btn-success\" type=\"submit\">Agregar</button></div>"
                + "</form>";
    }

    public static String error(String mensaje, String motor) {
        return pagina("Error", motor, alerta(mensaje)
                + "<a class=\"btn btn-outline-secondary\" href=\"/personajes\">Volver a la lista</a>");
    }
}
