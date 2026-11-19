package gestionrpg.modelo;

/** Un ítem dentro del inventario de un personaje. */
// >>> SALA 1.4 · Item: el ítem del inventario
public class Item {

    private final String nombre;
    private final String tipo;
    private final int valor;
    private final int cantidad;

    public Item(String nombre, String tipo, int valor, int cantidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.cantidad = cantidad;

// <<< FIN SALA 1.4
    }

    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getValor() { return valor; }
    public int getCantidad() { return cantidad; }

    @Override
    public String toString() { return "Item(" + nombre + " x" + cantidad + ")"; }
}
