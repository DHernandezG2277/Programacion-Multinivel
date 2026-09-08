from flask import Flask, render_template, request, redirect, url_for
from flask import Flask, jsonify
from Models.repositorio import Repositorio
from Models.personaje import Personaje

app = Flask(__name__)
repo=Repositorio()
repo.agregar_personaje(Personaje("Aragorn", "Humano", "Guerrero", 20))
repo.agregar_personaje(Personaje("Legolas", "Elfo", "Arquero", 18))

@app.route('/personajes', methods=['GET', 'POST'])
def get_personajes():
    if request.method == 'POST':
        nombre = request.form['nombre']
        raza = request.form['raza']
        clase = request.form['clase']
        nivel = int(request.form['nivel'])

        nuevo_personaje = Personaje(nombre, raza, clase, nivel)
        repo.agregar_personaje(nuevo_personaje)

        return redirect(url_for('get_personajes'))
    
    return render_template('personajes.html', personajes= repo.obtener_personajes())

if __name__ == '__main__':
    app.run(debug=True, port=5000)