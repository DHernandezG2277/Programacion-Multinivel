# RPG Manager · Persistencia · Proyecto esqueleto

Este repositorio trae el RPG Manager casi completo, en **Java (Javalin)** y en **Python (Flask)**.
Lo que falta son los bloques de la persistencia: los construyes en el **Edificio de la Persistencia**
y los pegas en los huecos marcados.

Derechos de autor © Francisco Cuéllar.

## Estructura

```
rpg_manager_multinivel_javalin/   proyecto Maven (NetBeans) · Java
rpg_manager_multinivel_flask/     proyecto Flask · Python
db/                               scripts de MariaDB y MongoDB (lee db/LEEME.md)
```

## Cómo se ve un hueco

```java
// >>> SALA 2.3 · MariaDB: leer un personaje
// Arma este bloque en el Edificio de la Persistencia (pestaña Java),
// pulsa «Copiar bloque» y pégalo entre estas dos marcas.
// <<< FIN SALA 2.3
```

En Python las marcas empiezan con `#`, en SQL con `--` y en la plantilla HTML van entre `{# … #}`.
Pega el bloque entre la línea `>>>` y la línea `<<<`; luego puedes borrar las cuatro líneas de comentario.

Hay **8 huecos en `db/`**, **21 en Java** y **21 en Python**.
El proyecto Java no compila y el de Python no arranca hasta que llenes todos sus huecos.
Para ver cuántos te faltan, busca `>>> SALA` en todo el proyecto (`grep -rn ">>> SALA" .`).

## Huecos por sala

| Sala | Tema | Java (en la Bóveda: scripts de `db/`) | Python |
|---|---|---|---|
| B.1 | Leer un personaje | `db/consultas_repositorio.sql`<br>`db/consultas_repositorio.js` | — |
| B.2 | El inventario: JOIN o documento | `db/consultas_repositorio.sql`<br>`db/consultas_repositorio.js` | — |
| B.3 | Llaves foráneas: el caso Thorgar | `db/consultas_repositorio.sql`<br>`db/consultas_repositorio.js` | — |
| B.4 | Preparar la persistencia | `db/alter_persistencia.sql`<br>`db/mongo_persistencia.js` | — |
| 1.1 | Personaje: la clase base | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/modelo/Personaje.java` | `rpg_manager_multinivel_flask/modelo/personaje.py` |
| 1.2 | Mago y Arquero: herencia | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/modelo/Mago.java` | `rpg_manager_multinivel_flask/modelo/personaje.py` |
| 1.3 | La fábrica de personajes | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/modelo/FabricaPersonajes.java` | `rpg_manager_multinivel_flask/modelo/personaje.py` |
| 1.4 | Item: el ítem del inventario | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/modelo/Item.java` | `rpg_manager_multinivel_flask/modelo/personaje.py` |
| 2.1 | El contrato | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/repositorio/PersonajeRepository.java` | `rpg_manager_multinivel_flask/repositorio/personaje_repository.py` |
| 2.2 | RepositorioMemoria | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/repositorio/RepositorioMemoria.java` | `rpg_manager_multinivel_flask/repositorio/repositorio_memoria.py` |
| 2.3 | MariaDB: leer un personaje | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/repositorio/RepositorioMariaDB.java` | `rpg_manager_multinivel_flask/repositorio/repositorio_mariadb.py` |
| 2.4 | MariaDB: guardar con INSERT | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/repositorio/RepositorioMariaDB.java` | `rpg_manager_multinivel_flask/repositorio/repositorio_mariadb.py` |
| 2.5 | MariaDB: eliminar y el error 1451 | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/repositorio/RepositorioMariaDB.java` | `rpg_manager_multinivel_flask/repositorio/repositorio_mariadb.py` |
| 2.6 | MongoDB: guardar con contador | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/repositorio/RepositorioMongo.java` | `rpg_manager_multinivel_flask/repositorio/repositorio_mongo.py` |
| 2.7 | El selector: fábrica y configuración | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/repositorio/RepositoryFactory.java` | `rpg_manager_multinivel_flask/repositorio/repository_factory.py` |
| 2.8 | Inventario: agregar un ítem | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/repositorio/RepositorioMongo.java` | `rpg_manager_multinivel_flask/repositorio/repositorio_mongo.py` |
| 3.1 | Inyección por constructor | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/servicio/PersonajeService.java` | `rpg_manager_multinivel_flask/servicio/personaje_service.py` |
| 3.2 | Obtener: de null a 404 | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/servicio/PersonajeService.java` | `rpg_manager_multinivel_flask/servicio/personaje_service.py` |
| 3.3 | Crear: validar y traducir | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/servicio/PersonajeService.java` | `rpg_manager_multinivel_flask/servicio/personaje_service.py` |
| 3.4 | Agregar ítem: reglas del negocio | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/servicio/PersonajeService.java` | `rpg_manager_multinivel_flask/servicio/personaje_service.py` |
| 4.1 | El arranque arma la cadena | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/Main.java` | `rpg_manager_multinivel_flask/app.py` |
| 4.2 | La ruta POST crear | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/controlador/PersonajeControlador.java` | `rpg_manager_multinivel_flask/controlador/personaje_controlador.py` |
| 4.3 | Las rutas del inventario | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/controlador/PersonajeControlador.java` | `rpg_manager_multinivel_flask/controlador/personaje_controlador.py` |
| 4.4 | La vista con Bootstrap | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/vista/PersonajeVista.java` | `rpg_manager_multinivel_flask/templates/detalle.html` |
| A.1 | Recorrido completo: Kaelen | `rpg_manager_multinivel_javalin/src/main/java/gestionrpg/RecorridoPersistencia.java` | `rpg_manager_multinivel_flask/recorrido.py` |

La guía de la actividad explica los pasos, el pasaporte, el trabajo escrito y la entrega.
