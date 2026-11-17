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
    SELECT p.id_personaje, p.nombre, c.nombre AS clase,
      p.nivel, p.vida, p.ataque, p.oro, p.mana, p.precision_tiro
      FROM personaje p
      JOIN clase c ON p.id_clase = c.id_clase
      WHERE p.id_personaje = 2;

-- <<< FIN SALA B.1

-- ── 2. El inventario de un personaje (listarInventario) ──────────────
-- inventario es la tabla intermedia N:M: el nombre del ítem vive en item.
-- >>> SALA B.2 · El inventario: JOIN o documento
        SELECT i.nombre, i.tipo, i.valor, inv.cantidad
          FROM inventario inv
          JOIN item i ON inv.id_item = i.id_item
         WHERE inv.id_personaje = 1
         ORDER BY i.nombre;

-- <<< FIN SALA B.2

-- ── 3. ¿Por qué no se puede borrar a Thorgar? (eliminar) ─────────────
-- combate apunta a personaje con tres llaves foráneas y sin CASCADE.
-- >>> SALA B.3 · Llaves foráneas: el caso Thorgar
    SELECT COUNT(*) AS combates_de_thorgar
      FROM combate
     WHERE id_atacante = 1
        OR id_defensor = 1
        OR id_ganador = 1;

-- <<< FIN SALA B.3
-- Esta línea falla con el error 1451 (llave foránea en uso):
-- DELETE FROM personaje WHERE id_personaje = 1;

--
--ALTER TABLE personaje
--  ADD COLUMN IF NOT EXISTS mana           INT NULL CHECK (mana >= 0),
--ADD COLUMN IF NOT EXISTS precision_tiro INT NULL CHECK (precision_tiro BETWEEN 0 AND 100);

-- Valores iniciales para los personajes que ya existían
--UPDATE personaje p JOIN clase c ON p.id_clase = c.id_clase
  -- SET p.mana = 100
-- WHERE c.nombre = 'Mago' AND p.mana IS NULL;
--

-- ── 4. Sumar un ítem al inventario (agregarItem) ─────────────────────
-- Si la llave (id_personaje, id_item) ya existe, suma la cantidad.
INSERT INTO inventario (id_personaje, id_item, cantidad)
SELECT 7, id_item, 2 FROM item WHERE nombre = 'Poción de vida'
    ON DUPLICATE KEY UPDATE cantidad = inventario.cantidad + VALUES(cantidad);
