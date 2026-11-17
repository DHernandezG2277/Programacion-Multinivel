package gestionrpg.modelo;

import java.math.BigDecimal;

public class Arquero extends Personaje {

    public static final int PRECISION_POR_DEFECTO = 70;

    private int precision;

    public Arquero(Integer id, String nombre, int nivel, int vida, int ataque, BigDecimal oro, int precision) {
        super(id, nombre, nivel, vida, ataque, oro);
        this.precision = precision;
    }

    @Override
    public String getClase() { return "Arquero"; }

    public int getPrecision() { return precision; }
}
