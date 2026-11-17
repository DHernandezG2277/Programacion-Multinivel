// =====================================================================
//  RPG MANAGER · Modelo de DOCUMENTOS (MongoDB) — mismos datos que rpg_manager_schema.sql
//  Pega este script completo en mongosh (Atlas / Compass > pestaña MONGOSH).
//  Se puede re-ejecutar: borra y recrea las colecciones.
//  © Francisco Germán Cuéllar Méndez · Derechos de autor reservados
// =====================================================================
db = db.getSiblingDB("rpg_manager");

db.personajes.drop();
db.misiones.drop();
db.combates.drop();

// Cada personaje es UN documento: su clase, inventario y misiones viajan DENTRO de él.
// Fíjate: los Magos tienen 'mana' y los Arqueros 'precision'; los Guerreros no. ¡Sin ALTER TABLE!
db.personajes.insertMany([
  {
    nombre: "Thorgar",
    clase: "Guerrero",
    nivel: 12,
    vida: 180,
    ataque: 35,
    oro: 450,
    creadoEn: ISODate("2026-08-03T10:15:00Z"),
    inventario: [
      { item: "Espada larga", tipo: "arma", valor: 120, cantidad: 1 },
      { item: "Cota de malla", tipo: "armadura", valor: 300, cantidad: 1 },
      { item: "Poción de vida", tipo: "pocion", valor: 25, cantidad: 3 }
    ],
    misiones: [
      { titulo: "Rescate en la Cripta", dificultad: "facil", recompensa: 100, estado: "completada" },
      { titulo: "El Bosque Susurrante", dificultad: "media", recompensa: 250, estado: "completada" },
      { titulo: "Asedio a Fortaleza Gris", dificultad: "dificil", recompensa: 600, estado: "aceptada" }
    ]
  },
  {
    nombre: "Elara",
    clase: "Mago",
    nivel: 15,
    vida: 90,
    ataque: 48,
    oro: 820,
    creadoEn: ISODate("2026-08-03T10:20:00Z"),
    mana: 150,
    inventario: [
      { item: "Báculo arcano", tipo: "arma", valor: 250, cantidad: 1 },
      { item: "Túnica encantada", tipo: "armadura", valor: 220, cantidad: 1 },
      { item: "Elixir de maná", tipo: "pocion", valor: 40, cantidad: 5 }
    ],
    misiones: [
      { titulo: "El Bosque Susurrante", dificultad: "media", recompensa: 250, estado: "completada" },
      { titulo: "La Torre del Archimago", dificultad: "dificil", recompensa: 700, estado: "completada" }
    ]
  },
  {
    nombre: "Kael",
    clase: "Arquero",
    nivel: 9,
    vida: 110,
    ataque: 30,
    oro: 210,
    creadoEn: ISODate("2026-08-05T14:00:00Z"),
    precision: 0.72,
    inventario: [
      { item: "Arco élfico", tipo: "arma", valor: 180, cantidad: 1 },
      { item: "Poción de vida", tipo: "pocion", valor: 25, cantidad: 2 }
    ],
    misiones: [
      { titulo: "Rescate en la Cripta", dificultad: "facil", recompensa: 100, estado: "completada" },
      { titulo: "El Bosque Susurrante", dificultad: "media", recompensa: 250, estado: "fallida" }
    ]
  },
  {
    nombre: "Brunhilda",
    clase: "Guerrero",
    nivel: 20,
    vida: 240,
    ataque: 52,
    oro: 1500,
    creadoEn: ISODate("2026-08-06T09:30:00Z"),
    inventario: [
      { item: "Espada larga", tipo: "arma", valor: 120, cantidad: 1 },
      { item: "Hacha de guerra", tipo: "arma", valor: 200, cantidad: 1 },
      { item: "Cota de malla", tipo: "armadura", valor: 300, cantidad: 1 },
      { item: "Poción de vida", tipo: "pocion", valor: 25, cantidad: 5 }
    ],
    misiones: [
      { titulo: "Rescate en la Cripta", dificultad: "facil", recompensa: 100, estado: "completada" },
      {
        titulo: "Asedio a Fortaleza Gris",
        dificultad: "dificil",
        recompensa: 600,
        estado: "completada"
      },
      { titulo: "Despertar del Dragón", dificultad: "legendaria", recompensa: 2000, estado: "aceptada" }
    ]
  },
  {
    nombre: "Merlinda",
    clase: "Mago",
    nivel: 4,
    vida: 60,
    ataque: 18,
    oro: 35,
    creadoEn: ISODate("2026-08-10T16:45:00Z"),
    mana: 60,
    inventario: [
      { item: "Elixir de maná", tipo: "pocion", valor: 40, cantidad: 1 }
    ],
    misiones: [
      { titulo: "Rescate en la Cripta", dificultad: "facil", recompensa: 100, estado: "aceptada" }
    ]
  },
  {
    nombre: "Sylvan",
    clase: "Arquero",
    nivel: 17,
    vida: 130,
    ataque: 44,
    oro: 990,
    creadoEn: ISODate("2026-08-11T11:10:00Z"),
    precision: 0.91,
    inventario: [
      { item: "Arco élfico", tipo: "arma", valor: 180, cantidad: 1 },
      { item: "Túnica encantada", tipo: "armadura", valor: 220, cantidad: 1 },
      { item: "Poción de vida", tipo: "pocion", valor: 25, cantidad: 4 }
    ],
    misiones: [
      { titulo: "El Bosque Susurrante", dificultad: "media", recompensa: 250, estado: "completada" },
      {
        titulo: "Asedio a Fortaleza Gris",
        dificultad: "dificil",
        recompensa: 600,
        estado: "completada"
      },
      { titulo: "Despertar del Dragón", dificultad: "legendaria", recompensa: 2000, estado: "fallida" }
    ]
  },
  {
    nombre: "Draven",
    clase: "Guerrero",
    nivel: 1,
    vida: 100,
    ataque: 10,
    oro: 0,
    creadoEn: ISODate("2026-09-20T08:00:00Z"),
    inventario: [],
    misiones: []
  },
  {
    nombre: "Nyx",
    clase: "Mago",
    nivel: 11,
    vida: 85,
    ataque: 40,
    oro: 300,
    creadoEn: ISODate("2026-08-15T19:25:00Z"),
    mana: 120,
    inventario: [
      { item: "Báculo arcano", tipo: "arma", valor: 250, cantidad: 1 },
      { item: "Elixir de maná", tipo: "pocion", valor: 40, cantidad: 2 }
    ],
    misiones: [
      { titulo: "El Bosque Susurrante", dificultad: "media", recompensa: 250, estado: "completada" }
    ]
  }
]);

// Catálogo de misiones (colección aparte, como referencia)
db.misiones.insertMany([
  { titulo: "Rescate en la Cripta", dificultad: "facil", recompensa: 100, nivelMinimo: 1 },
  { titulo: "El Bosque Susurrante", dificultad: "media", recompensa: 250, nivelMinimo: 5 },
  { titulo: "Asedio a Fortaleza Gris", dificultad: "dificil", recompensa: 600, nivelMinimo: 12 },
  { titulo: "La Torre del Archimago", dificultad: "dificil", recompensa: 700, nivelMinimo: 14 },
  { titulo: "Despertar del Dragón", dificultad: "legendaria", recompensa: 2000, nivelMinimo: 18 },
  { titulo: "Escolta de Caravana", dificultad: "facil", recompensa: 80, nivelMinimo: 1 }
]);

// Arena de Combate: guardamos los NOMBRES (desnormalizado) en vez de llaves foráneas
db.combates.insertMany([
  {
    atacante: "Thorgar",
    defensor: "Kael",
    ganador: "Thorgar",
    fecha: ISODate("2026-09-01T18:00:00Z")
  },
  {
    atacante: "Elara",
    defensor: "Thorgar",
    ganador: "Elara",
    fecha: ISODate("2026-09-02T18:30:00Z")
  },
  {
    atacante: "Brunhilda",
    defensor: "Sylvan",
    ganador: "Brunhilda",
    fecha: ISODate("2026-09-03T19:00:00Z")
  },
  { atacante: "Sylvan", defensor: "Nyx", ganador: "Sylvan", fecha: ISODate("2026-09-05T17:45:00Z") },
  { atacante: "Nyx", defensor: "Merlinda", ganador: "Nyx", fecha: ISODate("2026-09-06T20:10:00Z") },
  {
    atacante: "Brunhilda",
    defensor: "Elara",
    ganador: "Brunhilda",
    fecha: ISODate("2026-09-08T18:15:00Z")
  },
  {
    atacante: "Kael",
    defensor: "Merlinda",
    ganador: "Kael",
    fecha: ISODate("2026-09-09T16:00:00Z")
  },
  {
    atacante: "Sylvan",
    defensor: "Thorgar",
    ganador: "Thorgar",
    fecha: ISODate("2026-09-12T19:40:00Z")
  }
]);

// Comprobación rápida: debe mostrar { personajes: 8, misiones: 6, combates: 8 }
printjson({
  personajes: db.personajes.countDocuments(),
  misiones:   db.misiones.countDocuments(),
  combates:   db.combates.countDocuments()
});
