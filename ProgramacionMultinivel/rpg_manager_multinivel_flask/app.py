"""Punto de arranque del RPG Manager (Flask).

1. Lee config.ini (copia config.example.ini y pon tus datos).
2. La fábrica crea el repositorio que diga [app] repositorio.
3. El Service recibe ese repositorio por el constructor.
4. El controlador recibe el Service.
"""
import configparser
import os
import sys

from flask import Flask

from controlador.personaje_controlador import crear_controlador
from repositorio.repository_factory import crear_repositorio
from servicio.personaje_service import PersonajeService

CARPETA = os.path.dirname(os.path.abspath(__file__))


def leer_configuracion(ruta=None):
    ruta = ruta or os.path.join(CARPETA, "config.ini")
    if not os.path.exists(ruta):
        sys.exit("No existe config.ini. Copia config.example.ini como config.ini y pon tus datos.")
    cfg = configparser.ConfigParser()
    cfg.read(ruta, encoding="utf-8")
    return cfg


def crear_app(cfg=None, repositorio=None):
    # >>> SALA 4.1 · El arranque arma la cadena
    # Arma este bloque en el Edificio de la Persistencia (pestaña Python),
    # pulsa «Copiar bloque» y pégalo entre estas dos marcas.
    # <<< FIN SALA 4.1


if __name__ == "__main__":
    aplicacion = crear_app()
    aplicacion.run(port=5000, debug=False)
