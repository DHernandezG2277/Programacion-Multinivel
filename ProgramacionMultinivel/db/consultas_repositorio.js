// =====================================================================
//  RPG Manager · Las consultas que ejecuta RepositorioMongo
//  Pégalas en mongosh (Compass o Atlas) para ver qué hace tu repositorio
//  por dentro. Requiere rpg_manager_mongo_seed.js y mongo_persistencia.js.
//  Derechos de autor © Francisco Cuéllar
// =====================================================================
db = db.getSiblingDB("rpg_manager");

// ── 1. Leer un personaje (buscarPorId) ───────────────────────────────
// La clase viaja dentro del documento: no hace falta ningún JOIN.
// >>> SALA B.1 · Leer un personaje
// Arma este bloque en el Edificio de la Persistencia (pestaña MongoDB · JS),
// pulsa «Copiar bloque» y pégalo entre estas dos marcas.
// <<< FIN SALA B.1

// ── 2. El inventario de un personaje (listarInventario) ──────────────
// El inventario es un arreglo embebido: se pide solo ese campo.
// >>> SALA B.2 · El inventario: JOIN o documento
// Arma este bloque en el Edificio de la Persistencia (pestaña MongoDB · JS),
// pulsa «Copiar bloque» y pégalo entre estas dos marcas.
// <<< FIN SALA B.2

// ── 3. ¿Se puede borrar a Thorgar? (eliminar) ────────────────────────
// combates guarda NOMBRES, no llaves foráneas: nada impide el borrado.
// >>> SALA B.3 · Llaves foráneas: el caso Thorgar
// Arma este bloque en el Edificio de la Persistencia (pestaña MongoDB · JS),
// pulsa «Copiar bloque» y pégalo entre estas dos marcas.
// <<< FIN SALA B.3
// Esta línea SÍ funciona en MongoDB (cuidado: deja combates huérfanos):
// db.personajes.deleteOne({ id_personaje: 1 });

// ── 4. Sumar un ítem al inventario (agregarItem) ─────────────────────
// Paso 1: si ya lo tiene, $inc sobre ese elemento del arreglo.
const resultado = db.personajes.updateOne(
  { id_personaje: 7, "inventario.item": "Poción de vida" },
  { $inc: { "inventario.$.cantidad": 2 } }
);
// Paso 2: si no lo tenía, se copia del catálogo y se agrega con $push.
if (resultado.matchedCount === 0) {
  const delCatalogo = db.items.findOne({ nombre: "Poción de vida" });
  db.personajes.updateOne(
    { id_personaje: 7 },
    { $push: { inventario: { item: delCatalogo.nombre, tipo: delCatalogo.tipo,
                             valor: delCatalogo.valor, cantidad: 2 } } }
  );
}
