package gestionrpg.modelo;

import java.math.BigDecimal;

/**
 * Datos comunes a todos los personajes. El modelo no sabe nada de bases de datos.
 */
public abstract class Personaje {

    private Integer id;          // null hasta que el repositorio lo guarde
    private String nombre;
    private int nivel;
    private int vida;
    private int ataque;
    private BigDecimal oro;      // dinero: BigDecimal, nunca double (redondeo)

    // >>> SALA 1.1 · Personaje: la clase base
    // Arma este bloque en el Edificio de la Persistencia (pestaña Java),
    // pulsa «Copiar bloque» y pégalo entre estas dos marcas.
    // <<< FIN SALA 1.1
    }

    /** "Guerrero", "Mago" o "Arquero": lo define cada subclase. */
    public abstract String getClase();

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public int getNivel() { return nivel; }
    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = vida; }
    public int getAtaque() { return ataque; }
    public BigDecimal getOro() { return oro; }

    @Override
    public String toString() {
        return getClase() + "(id=" + id + ", nombre=" + nombre + ", nivel=" + nivel + ", vida=" + vida + ")";
    }
}
