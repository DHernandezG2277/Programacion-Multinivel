package gestionrpg;

import io.javalin.Javalin;
import gestionrpg.modelo.Personaje;
import gestionrpg.modelo.Repositorio;



public class App {
    public static void main(String[] args) 
    {
        Repositorio repo = new Repositorio();
        var app = Javalin.create().start(7070);
        app.get("/personajes", ctx -> 
        {
            String html = "<h1>Lista de Personajes</h1>";
            html += "<table border='1'><tr><th>Nombre</th><th>Clase</th><th>Nivel</th><th>Vida</th></tr>";
            for (Personaje p : repo.obtenerPersonajes()) {
                html += "<tr>";
                html += "<td>" + p.getNombre() + "</td>";
                html += "<td>" + p.getClase() + "</td>";
                html += "<td>" + p.getNivel() + "</td>";
                html += "<td>" + p.getVida() + "</td>";
                html += "</tr>";
            }
            html += "</table>";
            html += "<form action= '/personajes' method='POST'>";
                html += "<label>Nombre:</label>";
                html += "<input type='text' name='nombre'>";
                html += "<label>Clase:</label>";
                html += "<input type='text' name='clase'>";
                html += "<label>Nivel:</label>";
                html += "<input type='text' name='nivel'>";
                html += "<label>Vida:</label>";
                html += "<input type='text' name='vida'>";
                html += "<button type='submit'>Guardar Personaje</button>";
            html += "</form>";
            ctx.html(html);
        });
        app.post("/personajes", ctx->
        {
            String nombre = ctx.formParam("nombre");
            String clase = ctx.formParam("clase");
            int nivel = Integer.parseInt(ctx.formParam("nivel"));
            int vida = Integer.parseInt(ctx.formParam("vida"));
            
            Personaje nuevo = new Personaje(nombre, clase, nivel, vida);
            repo.agregarPersonaje(nuevo);
            
            ctx.redirect("/personajes");
        });
            
    }
}