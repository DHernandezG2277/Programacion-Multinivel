"""Contrato de la capa de persistencia.

Dice QUÉ operaciones debe ofrecer cualquier repositorio, no CÓMO se hacen.
Python no deja crear un objeto de una clase que no implemente todos los
métodos abstractos (lanza TypeError).
"""
from abc import ABC, abstractmethod


class PersonajeRepository(ABC):

    # >>> SALA 2.1 · El contrato
    # Arma este bloque en el Edificio de la Persistencia (pestaña Python),
    # pulsa «Copiar bloque» y pégalo entre estas dos marcas.
    # <<< FIN SALA 2.1

    @abstractmethod
    def listar_catalogo(self):
        """Devuelve todos los ítems que existen en el juego (la tabla item)."""

    @abstractmethod
    def agregar_item(self, id_personaje, nombre_item, cantidad):
        """Suma esa cantidad del ítem al inventario. Si ya lo tenía, se acumula.
        False si el ítem no existe en el catálogo."""

    @abstractmethod
    def quitar_item(self, id_personaje, nombre_item):
        """Saca el ítem completo del inventario. True si lo tenía."""
