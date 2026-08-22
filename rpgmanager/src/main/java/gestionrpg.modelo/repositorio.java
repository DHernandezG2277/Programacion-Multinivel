package gestionrpg.modelo;

import gestionrpg.modelo.personaje.Personaje;
import java.util.ArrayList;
import java.util.List;

public class Repositorio {
    private List<Personaje> personajes;

    public Repositorio() {
        this.personajes = new ArrayList<>();
    }

    public void agregarPersonaje(Personaje personaje) {
        personajes.add(personaje);
    }

    public List<Personaje> obtenerPersonajes() {
        return personajes;
    }
}