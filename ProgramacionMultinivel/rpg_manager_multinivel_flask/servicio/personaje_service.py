"""Capa de negocio: las reglas del juego.

Recibe el repositorio por el constructor y solo conoce el contrato
PersonajeRepository: no sabe si detrás hay memoria, MariaDB o MongoDB.
"""
from decimal import Decimal, InvalidOperation

from modelo.personaje import CLASES_VALIDAS, crear_personaje
from repositorio.excepciones import DatoDuplicadoException, DatoEnUsoException


class ValidacionException(Exception):
    """Un dato no cumple una regla del juego. El mensaje es para el usuario."""


class PersonajeNoEncontradoException(Exception):
    """No existe un personaje con ese id."""


# >>> SALA 3.1 · Inyección por constructor
# Arma este bloque en el Edificio de la Persistencia (pestaña Python),
# pulsa «Copiar bloque» y pégalo entre estas dos marcas.
# <<< FIN SALA 3.1

    def listar_por_clase(self, clase):
        self._validar_clase(clase)
        return self._repositorio.listar_por_clase(clase)

    # >>> SALA 3.2 · Obtener: de null a 404
    # Arma este bloque en el Edificio de la Persistencia (pestaña Python),
    # pulsa «Copiar bloque» y pégalo entre estas dos marcas.
    # <<< FIN SALA 3.2

    def catalogo(self):
        return self._repositorio.listar_catalogo()

    # ---------- inventario ----------

    def agregar_item(self, id, nombre_item, cantidad=1):
        # >>> SALA 3.4 · Agregar ítem: reglas del negocio
        # Arma este bloque en el Edificio de la Persistencia (pestaña Python),
        # pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        # <<< FIN SALA 3.4

    def quitar_item(self, id, nombre_item):
        personaje = self.obtener(id)
        if not self._repositorio.quitar_item(id, nombre_item):
            raise ValidacionException(f"{personaje.nombre} no tiene {nombre_item} en su inventario")

    # ---------- cambios ----------

    def crear(self, nombre, clase, nivel=1, vida=100, ataque=10, oro="0"):
        nombre = (nombre or "").strip()
        if not nombre:
            raise ValidacionException("El nombre es obligatorio")
        if len(nombre) > 50:
            raise ValidacionException("El nombre admite máximo 50 caracteres")
        self._validar_clase(clase)
        nivel = self._entero(nivel, "nivel")
        vida = self._entero(vida, "vida")
        ataque = self._entero(ataque, "ataque")
        oro = self._dinero(oro)
        if not 1 <= nivel <= 100:
            raise ValidacionException("El nivel debe estar entre 1 y 100")
        if vida < 0:
            raise ValidacionException("La vida no puede ser negativa")
        if ataque < 0:
            raise ValidacionException("El ataque no puede ser negativo")
        if oro < 0:
            raise ValidacionException("El oro no puede ser negativo")

        # >>> SALA 3.3 · Crear: validar y traducir
        # Arma este bloque en el Edificio de la Persistencia (pestaña Python),
        # pulsa «Copiar bloque» y pégalo entre estas dos marcas.
        # <<< FIN SALA 3.3

    def actualizar_vida(self, id, vida):
        vida = self._entero(vida, "vida")
        if vida < 0:
            raise ValidacionException("La vida no puede ser negativa")
        personaje = self.obtener(id)
        personaje.vida = vida
        self._repositorio.actualizar(personaje)
        return personaje

    def eliminar(self, id):
        self.obtener(id)
        try:
            self._repositorio.eliminar(id)
        except DatoEnUsoException:
            raise ValidacionException(
                "No se puede eliminar: el personaje tiene combates registrados")

    # ---------- validaciones auxiliares ----------

    @staticmethod
    def _validar_clase(clase):
        if clase not in CLASES_VALIDAS:
            raise ValidacionException(f"Clase inválida: {clase}. Usa Guerrero, Mago o Arquero")

    @staticmethod
    def _entero(valor, campo):
        try:
            return int(valor)
        except (TypeError, ValueError):
            raise ValidacionException(f"El campo {campo} debe ser un número entero")

    @staticmethod
    def _dinero(valor):
        try:
            return Decimal(str(valor)).quantize(Decimal("0.01"))
        except InvalidOperation:
            raise ValidacionException("El oro debe ser un número")
