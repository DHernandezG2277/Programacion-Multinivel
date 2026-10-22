def validar_personaje(personaje):
    if not personaje.get('nombre'):
        return False, "Error: El nombre es obligatorio."
    if not personaje.get('raza'):
        return False, "Error: La raza es obligatoria."
    if not personaje.get('clase'):
        return False, "Error: La clase es obligatoria."
    if personaje.get('nivel') < 1 or personaje.get('nivel') > 100:
        return False, "Error: El nivel debe ser un número entero entre 1 y 100."
    
    return True, "Personaje válido."