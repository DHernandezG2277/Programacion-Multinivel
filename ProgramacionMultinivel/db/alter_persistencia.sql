-- ─────────────────────────────────────────────────────────────────────
-- RPG Manager · Persistencia (semanas 7-10) · cambio de esquema
-- Ejecutar DESPUÉS de rpg_manager_schema.sql (el de la nivelación).
--
-- Decisión de diseño: la tabla personaje no tenía dónde guardar el mana del
-- Mago ni la precisión del Arquero. Se agregan dos columnas opcionales (NULL
-- para las clases que no las usan), como en MongoDB donde esos campos solo
-- existen en algunos documentos.
--
-- La columna se llama precision_tiro porque PRECISION es palabra reservada
-- en MariaDB.
-- Se puede ejecutar varias veces sin error.
-- ─────────────────────────────────────────────────────────────────────
USE rpg_manager;

-- >>> SALA B.4 · Preparar la persistencia
ALTER TABLE personaje
  ADD COLUMN IF NOT EXISTS mana           INT NULL CHECK (mana >= 0),
  ADD COLUMN IF NOT EXISTS precision_tiro INT NULL CHECK (precision_tiro BETWEEN 0 AND 100);

-- Valores iniciales para los personajes que ya existían
UPDATE personaje p JOIN clase c ON p.id_clase = c.id_clase
   SET p.mana = 100
 WHERE c.nombre = 'Mago' AND p.mana IS NULL;
-- <<< FIN SALA B.4

UPDATE personaje p JOIN clase c ON p.id_clase = c.id_clase
   SET p.precision_tiro = 70
 WHERE c.nombre = 'Arquero' AND p.precision_tiro IS NULL;

-- Verificación: Magos con mana y Arqueros con precisión
SELECT p.nombre, c.nombre AS clase, p.mana, p.precision_tiro
  FROM personaje p JOIN clase c ON p.id_clase = c.id_clase
 ORDER BY p.id_personaje;
