# Carpeta db/

Scripts de la base de datos del RPG Manager: los de la nivelación de SQL y NoSQL y los ajustes del bloque de persistencia.

| Archivo | Motor | De dónde sale | Para qué |
| --- | --- | --- | --- |
| `rpg_manager_schema.sql` | MariaDB | Nivelación | Crea la base `rpg_manager` con sus 7 tablas y datos de prueba |
| `alter_persistencia.sql` | MariaDB | Persistencia | Agrega las columnas `mana` y `precision_tiro` a `personaje` |
| `rpg_manager_mongo_seed.js` | MongoDB | Nivelación | Carga las colecciones `personajes`, `misiones` y `combates` |
| `mongo_persistencia.js` | MongoDB | Persistencia | Pasa la precisión a escala 0-100, numera los personajes, crea los índices y el catálogo `items` |
| `consultas_repositorio.sql` | MariaDB | Persistencia | Las consultas que ejecuta `RepositorioMariaDB`, para probarlas a mano |
| `consultas_repositorio.js` | MongoDB | Persistencia | Las consultas que ejecuta `RepositorioMongo`, para probarlas a mano |

## Orden en MariaDB

1. `rpg_manager_schema.sql`: al final muestra los conteos **3 · 8 · 9 · 18 · 6 · 15 · 8**
   (clases, personajes, ítems, inventario, misiones, personaje_mision, combates).
2. `alter_persistencia.sql`: al final muestra los Magos con mana y los Arqueros con precisión.

En HeidiSQL: Archivo → Ejecutar archivo SQL. En consola:
`mariadb -u root -p --default-character-set=utf8mb4 < rpg_manager_schema.sql`

## Orden en MongoDB (mongosh, Compass o Atlas)

1. `rpg_manager_mongo_seed.js`: al final muestra `{ personajes: 8, misiones: 6, combates: 8 }`.
2. `mongo_persistencia.js`: al final muestra los 8 personajes con su `id_personaje` del 1 al 8.

## Para ver qué hace el repositorio por dentro

Con las bases ya cargadas, ejecuta `consultas_repositorio.sql` (MariaDB) o pega `consultas_repositorio.js` en mongosh.
Son las mismas consultas del Edificio de la Persistencia: leer un personaje, ver su inventario,
el caso de borrar a Thorgar y sumar un ítem al inventario. La consulta 4 cambia datos (le suma 2 pociones de vida a Draven, el personaje 7).

Si se dañan los datos, se re-ejecutan los dos scripts de cada motor en el mismo orden.
Todos se pueden ejecutar varias veces sin error.
