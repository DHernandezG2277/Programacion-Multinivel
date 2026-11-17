package gestionrpg.modelo;

/** Un ítem dentro del inventario de un personaje. */
// >>> SALA 1.4 · Item: el ítem del inventario
// Arma este bloque en el Edificio de la Persistencia (pestaña Java),
// pulsa «Copiar bloque» y pégalo entre estas dos marcas.
// <<< FIN SALA 1.4
    }

    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getValor() { return valor; }
    public int getCantidad() { return cantidad; }

    @Override
    public String toString() { return "Item(" + nombre + " x" + cantidad + ")"; }
}
