"""Modelo del RPG Manager: Personaje y sus subclases, e Item del inventario.

El modelo no sabe nada de bases de datos: solo describe los objetos del juego.
"""
from decimal import Decimal

CLASES_VALIDAS = ("Guerrero", "Mago", "Arquero")

MANA_POR_DEFECTO = 100       # Mago sin mana guardado (por ejemplo, filas antiguas en MariaDB)
PRECISION_POR_DEFECTO = 70   # Arquero sin precision guardada


class Personaje:
    """Datos comunes a todos los personajes."""

    clase = "Personaje"

    # >>> SALA 1.1 · Personaje: la clase base
    # Arma este bloque en el Edificio de la Persistencia (pestaña Python),
    # pulsa «Copiar bloque» y pégalo entre estas dos marcas.
    # <<< FIN SALA 1.1

    def a_dict(self):
        """Representación simple para la API JSON y las plantillas."""
        return {
            "id": self.id,
            "nombre": self.nombre,
            "clase": self.clase,
            "nivel": self.nivel,
            "vida": self.vida,
            "ataque": self.ataque,
            "oro": str(self.oro),
        }

    def __repr__(self):
        return f"{self.clase}(id={self.id}, nombre={self.nombre!r}, nivel={self.nivel}, vida={self.vida})"


class Guerrero(Personaje):
    clase = "Guerrero"


class Mago(Personaje):
    clase = "Mago"

    # >>> SALA 1.2 · Mago y Arquero: herencia
    # Arma este bloque en el Edificio de la Persistencia (pestaña Python),
    # pulsa «Copiar bloque» y pégalo entre estas dos marcas.
    # <<< FIN SALA 1.2


class Arquero(Personaje):
    clase = "Arquero"

    def __init__(self, nombre, nivel=1, vida=100, ataque=10, oro=Decimal("0"), id=None,
                 precision=PRECISION_POR_DEFECTO):
        super().__init__(nombre, nivel, vida, ataque, oro, id)
        self.precision = precision

    def a_dict(self):
        datos = super().a_dict()
        datos["precision"] = self.precision
        return datos


def crear_personaje(clase, nombre, nivel=1, vida=100, ataque=10, oro=Decimal("0"), id=None,
                    mana=None, precision=None):
    """Reconstruye la subclase correcta a partir del texto de la clase.

    La usan los tres repositorios: una fila de MariaDB, un documento de MongoDB
    o un formulario dicen "Mago", y aquí se decide qué objeto crear.
    """
    # >>> SALA 1.3 · La fábrica de personajes
    # Arma este bloque en el Edificio de la Persistencia (pestaña Python),
    # pulsa «Copiar bloque» y pégalo entre estas dos marcas.
    # <<< FIN SALA 1.3


# >>> SALA 1.4 · Item: el ítem del inventario
# Arma este bloque en el Edificio de la Persistencia (pestaña Python),
# pulsa «Copiar bloque» y pégalo entre estas dos marcas.
# <<< FIN SALA 1.4

    def a_dict(self):
        return {"nombre": self.nombre, "tipo": self.tipo, "valor": self.valor, "cantidad": self.cantidad}

    def __repr__(self):
        return f"Item({self.nombre!r} x{self.cantidad})"
