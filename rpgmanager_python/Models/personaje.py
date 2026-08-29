class Personaje:
    def __init__(self, nombre, raza, clase, nivel):
        self.nombre = nombre
        self.raza = raza
        self.clase = clase
        self.nivel = nivel

    def Dictar(self):
        return {
            "nombre": self.nombre,
            "raza": self.raza,
            "clase": self.clase,
            "nivel": self.nivel
        }