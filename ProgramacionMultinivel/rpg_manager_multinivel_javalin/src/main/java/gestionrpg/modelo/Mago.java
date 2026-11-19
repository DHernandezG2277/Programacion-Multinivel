package gestionrpg.modelo;

import java.math.BigDecimal;

public class Mago extends Personaje {

    public static final int MANA_POR_DEFECTO = 100;

    private int mana;

    // >>> SALA 1.2 · Mago y Arquero: herencia
        public Mago(Integer id, String nombre, int nivel, int vida, int ataque, BigDecimal oro, int mana) {
        super(id, nombre, nivel, vida, ataque, oro);
        this.mana = mana;
        }

            @Override
            public String getClase() { return "Mago"; }
        
            public int getMana() { return mana; }

    // <<< FIN SALA 1.2
}
