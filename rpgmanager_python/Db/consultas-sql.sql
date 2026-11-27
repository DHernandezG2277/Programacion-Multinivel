
//Creacion de tablas

CREATE TABLE clase(
id_clase INT PRIMARY KEY,
nombre VARCHAR(15),
descripcion TEXT
);

CREATE TABLE item (
    id_item INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) UNIQUE,
    tipo ENUM('arma', 'armadura', 'pocion', 'reliquia'),
    precio DECIMAL(10,2),
    peso DECIMAL(5,2)
);

CREATE TABLE personaje (
    id_personaje INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30),
    id_clase INT,
    nivel INT CHECK (nivel BETWEEN 1 AND 100),
    vida INT CHECK (vida >= 0),
   FOREIGN KEY (id_clase) REFERENCES clase(id_clase)
);

CREATE TABLE inventario(
	id_personaje INT NOT NULL,
	id_item INT NOT NULL,
	cantidad_item INT NOT NULL,
	FOREIGN KEY (id_personaje) REFERENCES personaje(id_personaje),
	FOREIGN KEY (id_item) REFERENCES item(id_item),
	PRIMARY KEY (id_personaje, id_item)
	);


//Insertar valores dentro de cada tabla 

                        //id clase, nombre, descripcion
INSERT INTO clase() VALUES (04,"Picaro","Sigiloso y ágil, pero sin defensa");


INSERT INTO personaje(nombre, id_clase, nivel, vida) VALUES ("elreptil", 1, 5, 100);


INSERT INTO item(nombre, tipo, precio, peso) VALUES ("Amuleto de suerte", "reliquia", 200.00, 0.5);

                            //id personaje, id item, cantidad item
INSERT INTO inventario VALUES (4, 1, 2);


//Mostrar contenidos de las tablas

SELECT * FROM clase;

SELECT nombre, vida FROM personaje;

SELECT nombre, vida FROM personaje WHERE vida>=50;

SELECT * FROM personaje ORDER BY nivel DESC LIMIT 1; 

SELECT * FROM inventario i JOIN personaje p ON (i.id_personaje=p.id_personaje);

SELECT  i.id_personaje, p.nombre, nivel, vida, c.nombre, it.nombre, tipo, precio, peso
FROM inventario i 
JOIN personaje p ON i.id_personaje = p.id_personaje 
JOIN item it ON i.id_item = it.id_item
JOIN clase c ON p.id_clase = c.id_clase;


// Modificar las tablas

ALTER TABLE inventario ADD nombre_personaje VARCHAR(30);

ALTER TABLE inventario DROP nombre_personaje;


