-- =====================================================================
--  RPG MANAGER · Base de datos relacional (MariaDB 10.x / MySQL 8.x)
--  Taller SQL básico/intermedio + comparativo NoSQL · Programación Multinivel
--  Ejecuta TODO este script una sola vez (se puede re-ejecutar: borra y recrea).
--  © Francisco Germán Cuéllar Méndez · Derechos de autor reservados
-- =====================================================================

-- Obliga a la conexión a usar UTF-8 (evita que 'Poción' se guarde como 'PociÃ³n')
SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS rpg_manager
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE rpg_manager;

-- Borrado en orden inverso a las dependencias (hijas primero)
DROP VIEW  IF EXISTS v_ficha_personaje;
DROP TABLE IF EXISTS combate;
DROP TABLE IF EXISTS personaje_mision;
DROP TABLE IF EXISTS inventario;
DROP TABLE IF EXISTS mision;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS personaje;
DROP TABLE IF EXISTS clase;

-- ─────────────────────────────────────────────────────────────────────
-- 1. TABLAS (DDL)
-- ─────────────────────────────────────────────────────────────────────
CREATE TABLE clase (
  id_clase     INT AUTO_INCREMENT PRIMARY KEY,
  nombre       VARCHAR(30)  NOT NULL UNIQUE,
  descripcion  VARCHAR(150)
);

CREATE TABLE personaje (
  id_personaje INT AUTO_INCREMENT PRIMARY KEY,
  nombre       VARCHAR(50)  NOT NULL UNIQUE,
  id_clase     INT          NOT NULL,
  nivel        INT          NOT NULL DEFAULT 1   CHECK (nivel BETWEEN 1 AND 100),
  vida         INT          NOT NULL DEFAULT 100 CHECK (vida >= 0),
  ataque       INT          NOT NULL DEFAULT 10,
  oro          DECIMAL(10,2) NOT NULL DEFAULT 0  CHECK (oro >= 0),
  creado_en    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_personaje_clase FOREIGN KEY (id_clase) REFERENCES clase(id_clase)
);

CREATE TABLE item (
  id_item      INT AUTO_INCREMENT PRIMARY KEY,
  nombre       VARCHAR(50)  NOT NULL UNIQUE,
  tipo         ENUM('arma','armadura','pocion','reliquia') NOT NULL,
  valor        INT          NOT NULL CHECK (valor >= 0),
  peso         DECIMAL(5,2) NOT NULL DEFAULT 0
);

-- Relación N:M  personaje <-> item  (un personaje tiene muchos ítems y
-- un ítem puede estar en el inventario de muchos personajes)
CREATE TABLE inventario (
  id_personaje INT NOT NULL,
  id_item      INT NOT NULL,
  cantidad     INT NOT NULL DEFAULT 1 CHECK (cantidad > 0),
  PRIMARY KEY (id_personaje, id_item),
  CONSTRAINT fk_inv_personaje FOREIGN KEY (id_personaje)
      REFERENCES personaje(id_personaje) ON DELETE CASCADE,
  CONSTRAINT fk_inv_item FOREIGN KEY (id_item)
      REFERENCES item(id_item)
);

CREATE TABLE mision (
  id_mision      INT AUTO_INCREMENT PRIMARY KEY,
  titulo         VARCHAR(80) NOT NULL,
  dificultad     ENUM('facil','media','dificil','legendaria') NOT NULL,
  recompensa_oro INT NOT NULL DEFAULT 0,
  nivel_minimo   INT NOT NULL DEFAULT 1
);

-- Relación N:M  personaje <-> mision, con un dato propio: el estado
CREATE TABLE personaje_mision (
  id_personaje INT NOT NULL,
  id_mision    INT NOT NULL,
  estado       ENUM('aceptada','completada','fallida') NOT NULL DEFAULT 'aceptada',
  PRIMARY KEY (id_personaje, id_mision),
  CONSTRAINT fk_pm_personaje FOREIGN KEY (id_personaje)
      REFERENCES personaje(id_personaje) ON DELETE CASCADE,
  CONSTRAINT fk_pm_mision FOREIGN KEY (id_mision)
      REFERENCES mision(id_mision)
);

-- Arena de Combate: tres llaves foráneas hacia la MISMA tabla personaje
CREATE TABLE combate (
  id_combate   INT AUTO_INCREMENT PRIMARY KEY,
  id_atacante  INT NOT NULL,
  id_defensor  INT NOT NULL,
  id_ganador   INT NOT NULL,
  fecha        DATETIME NOT NULL,
  CONSTRAINT fk_cb_atacante FOREIGN KEY (id_atacante) REFERENCES personaje(id_personaje),
  CONSTRAINT fk_cb_defensor FOREIGN KEY (id_defensor) REFERENCES personaje(id_personaje),
  CONSTRAINT fk_cb_ganador  FOREIGN KEY (id_ganador)  REFERENCES personaje(id_personaje)
);

-- ─────────────────────────────────────────────────────────────────────
-- 2. DATOS DE PRUEBA (DML)
-- ─────────────────────────────────────────────────────────────────────
INSERT INTO clase (nombre, descripcion) VALUES
  ('Guerrero', 'Combate cuerpo a cuerpo, mucha vida'),
  ('Mago',     'Ataque mágico alto, poca vida'),
  ('Arquero',  'Ataque a distancia, equilibrado');

INSERT INTO personaje (nombre, id_clase, nivel, vida, ataque, oro, creado_en) VALUES
  ('Thorgar',   1, 12, 180, 35,  450.00, '2026-08-03 10:15:00'),
  ('Elara',     2, 15,  90, 48,  820.00, '2026-08-03 10:20:00'),
  ('Kael',      3,  9, 110, 30,  210.00, '2026-08-05 14:00:00'),
  ('Brunhilda', 1, 20, 240, 52, 1500.00, '2026-08-06 09:30:00'),
  ('Merlinda',  2,  4,  60, 18,   35.00, '2026-08-10 16:45:00'),
  ('Sylvan',    3, 17, 130, 44,  990.00, '2026-08-11 11:10:00'),
  ('Draven',    1,  1, 100, 10,    0.00, '2026-09-20 08:00:00'),
  ('Nyx',       2, 11,  85, 40,  300.00, '2026-08-15 19:25:00');

INSERT INTO item (nombre, tipo, valor, peso) VALUES
  ('Espada larga',       'arma',      120,  3.50),
  ('Hacha de guerra',    'arma',      200,  5.00),
  ('Báculo arcano',      'arma',      250,  2.00),
  ('Arco élfico',        'arma',      180,  1.50),
  ('Cota de malla',      'armadura',  300, 12.00),
  ('Túnica encantada',   'armadura',  220,  1.00),
  ('Poción de vida',     'pocion',     25,  0.30),
  ('Elixir de maná',     'pocion',     40,  0.30),
  ('Amuleto del dragón', 'reliquia', 1000,  0.20);

INSERT INTO inventario (id_personaje, id_item, cantidad) VALUES
  (1,1,1),(1,5,1),(1,7,3),
  (2,3,1),(2,6,1),(2,8,5),
  (3,4,1),(3,7,2),
  (4,2,1),(4,5,1),(4,7,5),(4,1,1),
  (5,8,1),
  (6,4,1),(6,6,1),(6,7,4),
  (8,3,1),(8,8,2);
  -- Draven (7) no tiene ítems · nadie tiene el Amuleto del dragón (9)

INSERT INTO mision (titulo, dificultad, recompensa_oro, nivel_minimo) VALUES
  ('Rescate en la Cripta',    'facil',       100,  1),
  ('El Bosque Susurrante',    'media',       250,  5),
  ('Asedio a Fortaleza Gris', 'dificil',     600, 12),
  ('La Torre del Archimago',  'dificil',     700, 14),
  ('Despertar del Dragón',    'legendaria', 2000, 18),
  ('Escolta de Caravana',     'facil',        80,  1);

INSERT INTO personaje_mision (id_personaje, id_mision, estado) VALUES
  (1,1,'completada'),(1,2,'completada'),(1,3,'aceptada'),
  (2,2,'completada'),(2,4,'completada'),
  (3,1,'completada'),(3,2,'fallida'),
  (4,1,'completada'),(4,3,'completada'),(4,5,'aceptada'),
  (5,1,'aceptada'),
  (6,2,'completada'),(6,3,'completada'),(6,5,'fallida'),
  (8,2,'completada');
  -- Nadie ha tomado 'Escolta de Caravana' (6)

INSERT INTO combate (id_atacante, id_defensor, id_ganador, fecha) VALUES
  (1, 3, 1, '2026-09-01 18:00:00'),
  (2, 1, 2, '2026-09-02 18:30:00'),
  (4, 6, 4, '2026-09-03 19:00:00'),
  (6, 8, 6, '2026-09-05 17:45:00'),
  (8, 5, 8, '2026-09-06 20:10:00'),
  (4, 2, 4, '2026-09-08 18:15:00'),
  (3, 5, 3, '2026-09-09 16:00:00'),
  (6, 1, 1, '2026-09-12 19:40:00');

-- Comprobación rápida: debe mostrar 3 · 8 · 9 · 18 · 6 · 15 · 8
SELECT (SELECT COUNT(*) FROM clase)            AS clases,
       (SELECT COUNT(*) FROM personaje)        AS personajes,
       (SELECT COUNT(*) FROM item)             AS items,
       (SELECT COUNT(*) FROM inventario)       AS inventario,
       (SELECT COUNT(*) FROM mision)           AS misiones,
       (SELECT COUNT(*) FROM personaje_mision) AS personaje_mision,
       (SELECT COUNT(*) FROM combate)          AS combates;
