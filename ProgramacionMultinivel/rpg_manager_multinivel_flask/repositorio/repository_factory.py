"""Selector: lee la configuración y crea la implementación del contrato.

Es el único lugar del proyecto que sabe qué motor hay detrás.
"""
from repositorio.repositorio_mariadb import RepositorioMariaDB
from repositorio.repositorio_memoria import RepositorioMemoria
from repositorio.repositorio_mongo import RepositorioMongo


def crear_repositorio(cfg):
    # >>> SALA 2.7 · El selector: fábrica y configuración
    # Arma este bloque en el Edificio de la Persistencia (pestaña Python),
    # pulsa «Copiar bloque» y pégalo entre estas dos marcas.
    # <<< FIN SALA 2.7
