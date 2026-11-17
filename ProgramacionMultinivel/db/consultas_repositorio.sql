-- ─────────────────────────────────────────────────────────────────────
-- RPG Manager · Las consultas que ejecuta RepositorioMariaDB
-- Ejecútalas en HeidiSQL o en la consola para ver qué hace tu repositorio
-- por dentro. Requiere rpg_manager_schema.sql y alter_persistencia.sql.
-- Derechos de autor © Francisco Cuéllar
-- ─────────────────────────────────────────────────────────────────────
USE rpg_manager;

-- ── 1. Leer un personaje (buscarPorId) ───────────────────────────────
-- La clase no está como texto en personaje: llega desde la tabla clase.
-- >>> SALA B.1 · Leer un personaje
-- Arma este bloque en el Edificio de la Persistencia (pestaña MariaDB · SQL),
-- pulsa «Copiar bloque» y pégalo entre estas dos marcas.
-- <<< FIN SALA B.1

-- ── 2. El inventario de un personaje (listarInventario) ──────────────
-- inventario es la tabla intermedia N:M: el nombre del ítem vive en item.
-- >>> SALA B.2 · El inventario: JOIN o documento
-- Arma este bloque en el Edificio de la Persistencia (pestaña MariaDB · SQL),
-- pulsa «Copiar bloque» y pégalo entre estas dos marcas.
-- <<< FIN SALA B.2

-- ── 3. ¿Por qué no se puede borrar a Thorgar? (eliminar) ─────────────
-- combate apunta a personaje con tres llaves foráneas y sin CASCADE.
-- >>> SALA B.3 · Llaves foráneas: el caso Thorgar
-- Arma este bloque en el Edificio de la Persistencia (pestaña MariaDB · SQL),
-- pulsa «Copiar bloque» y pégalo entre estas dos marcas.
-- <<< FIN SALA B.3
-- Esta línea falla con el error 1451 (llave foránea en uso):
-- DELETE FROM personaje WHERE id_personaje = 1;

-- ── 4. Sumar un ítem al inventario (agregarItem) ─────────────────────
-- Si la llave (id_personaje, id_item) ya existe, suma la cantidad.
INSERT INTO inventario (id_personaje, id_item, cantidad)
SELECT 7, id_item, 2 FROM item WHERE nombre = 'Poción de vida'
    ON DUPLICATE KEY UPDATE cantidad = inventario.cantidad + VALUES(cantidad);
