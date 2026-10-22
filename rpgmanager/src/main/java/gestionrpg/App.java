package gestionrpg;

import io.javalin.Javalin;
import java.util.Map;
import java.util.HashMap;
import gestionrpg.modelo.Personaje;
import gestionrpg.modelo.Repositorio;
import PersonajeServicio.Verificador;
import java.util.List;



public class App {
    public static void main(String[] args) 
    {
        Repositorio repo = new Repositorio();
        var app = Javalin.create().start(7070);
        app.get("/personajes", ctx -> 
        {
            List<Personaje> listaPersonajes = repo.obtenerPersonajes();
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("Personajes", listaPersonajes);
            
            ctx.render("Templates/personajes.html", modelo);
        });
        app.post("/personajes", ctx->
        {
            Verificador verify = new Verificador();
            String nombre = ctx.formParam("nombre");
            String clase = ctx.formParam("clase");
            int nivel = Integer.parseInt(ctx.formParam("nivel"));
            int vida = Integer.parseInt(ctx.formParam("vida"));
            
            if (verify.verificar(nombre, clase, nivel, vida) == false)
            {
               System.out.println("Error...");
               ctx.status(400);
            }
            Personaje nuevo = new Personaje(nombre, clase, nivel, vida);
            repo.agregarPersonaje(nuevo);
            
            ctx.redirect("/personajes");
        });
            
    }
}