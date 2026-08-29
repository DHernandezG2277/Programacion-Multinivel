
package gestionrpg.modelo;

public class Personaje {
    private String nombre;
    private String clase;
    private int nivel;
    private int vida;

    public Personaje(String nombre, String clase, int nivel, int vida) {
        this.nombre = nombre;
        this.clase = clase;
        this.nivel = nivel;
        this.vida = vida;
    }
    
    public void setNombre(String nombre) 
        {
            this.nombre = nombre;
        }
    public String getNombre() 
        {
            return this.nombre;
        }

    public void setClase(String clase)
        {
            this.clase = clase;
        }
    public String getClase()
        {
        return this.clase;
        }

    public void setNivel(int nivel)
        {
        this.nivel = nivel;
        }
    public int getNivel()
        {
        return this.nivel;
        }
    
    public void setVida(int vida) 
        {
        this.vida = vida;
        }
    public int getVida() 
        {
        return this.vida;
        }

}