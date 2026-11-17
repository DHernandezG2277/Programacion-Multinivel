package gestionrpg.modelo;

import java.math.BigDecimal;

public class Guerrero extends Personaje {

    public Guerrero(Integer id, String nombre, int nivel, int vida, int ataque, BigDecimal oro) {
        super(id, nombre, nivel, vida, ataque, oro);
    }

    @Override
    public String getClase() { return "Guerrero"; }
}
