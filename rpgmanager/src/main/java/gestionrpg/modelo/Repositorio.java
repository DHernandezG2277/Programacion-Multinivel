package gestionrpg.modelo;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {
    private List<Personaje> personajes;

    public Repositorio() {
        this.personajes = new ArrayList<>();
        this.personajes.add(new Personaje("Aragorn", "Guerrero", 10, 100));
        this.personajes.add(new Personaje("Gandalf", "Mago", 15, 80));
    }

    public void agregarPersonaje(Personaje personaje) {
        personajes.add(personaje);
    }

    public List<Personaje> obtenerPersonajes() {
        return personajes;
    }
}