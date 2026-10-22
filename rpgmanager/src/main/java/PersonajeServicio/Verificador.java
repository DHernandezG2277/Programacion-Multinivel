package PersonajeServicio;

/**
 *
 * @author Hernández
 */
public class Verificador{
    
    public boolean verificar(String nombre,String clase,int nivel,int vida)
    {
        if (nombre.isEmpty())
        {
            System.out.println("Error: el nombre es obligatorio");
            return false;
        }
        if (clase.isEmpty())
        {
            System.out.println("Error: la clase es obligatoria");
            return false;
        }
        if (nivel < 1 || nivel > 100)
        {
            System.out.println("Error: el nivel no puede ser menor de 1 ni mayor de 100");
            return false;
        }
        if (vida < 1 || vida > 150)
        {
            System.out.println("Error: la vida no puede menor de 1 ni mayor de 150");
            return false;
        }
        return true;
    }
    
}
