package gestionrpg;

import io.javalin.Javalin;
import gestionrpg.modelo.Repositorio;


public class App {
    public static void main(String[] args) {
        Repositorio repo = new Repositorio();
        var app = Javalin.create().start(7070);
        app.get("/personajes", ctx -> 
        {
            ctx.json(repo.obtenerPersonajes());
        });
    }
}