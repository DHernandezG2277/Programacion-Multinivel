from flask import Flask, render_template, request, redirect, url_for
from flask import Flask, jsonify, Blueprint, request
from Models.repositorio import Repositorio
from Models.personaje import Personaje
from service.personaje_service import validar_personaje

app = Flask(__name__)
repo=Repositorio()
personajes_bp = Blueprint('personajes', __name__)

@app.route('/personajes', methods=['GET', 'POST'])
def crear_personajes():
    if request.method == 'POST':
        datos_nuevos = {
            "nombre": request.form.get('nombre'),
            "raza": request.form.get('raza'),
            "clase": request.form.get('clase'),
            "nivel": int(request.form.get('nivel', 0))
        }
        es_valido, mensaje = validar_personaje(datos_nuevos)
        if not es_valido:
            return render_template('personajes.html', mensaje=mensaje, personajes=repo.obtener_personajes()), 400
        repo.agregar_personaje(Personaje(**datos_nuevos))
        return redirect(url_for('crear_personajes'))

    return render_template('personajes.html', personajes=repo.obtener_personajes())

if __name__ == '__main__':
    app.run(debug=True, port=5000)