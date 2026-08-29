from flask import Flask, jsonify
from Models.repositorio import Repositorio
from Models.personaje import Personaje

app = Flask(__name__)
repo=Repositorio()
repo.agregar_personaje(Personaje("Aragorn", "Humano", "Guerrero", 20))
repo.agregar_personaje(Personaje("Legolas", "Elfo", "Arquero", 18))

@app.route('/personajes', methods=['GET'])
def get_personajes():
    cambio= [personaje.Dictar() for personaje in repo.obtener_personajes()]
    return jsonify(cambio)

if __name__ == '__main__':
    app.run(debug=True, port=5000)