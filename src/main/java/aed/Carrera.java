package aed;

/**
 * Clase que representa una carrera.
 *
 * Invariante de Representación:
 * - El nombre de la carrera no puede ser nulo o un string vacío. 
 * - El diccionario de materias debe ser una instancia no nula de la clase
 * DiccionarioDigital, donde las claves son los nombres de las materias y los
 * valores son objetos de la clase Materia.
 *
 */
public class Carrera {

    String nombreCarrera;
    DiccionarioDigital<String, Materia> materias;

    public Carrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
        this.materias = new DiccionarioDigital<>();
    }

    public String getNombreCarrera() {
        return this.nombreCarrera;
    }

    public DiccionarioDigital<String, Materia> getMaterias() {
        return this.materias;
    }

    public void agregarMateria(Materia materia) {
        materias.definir(materia.getNombreMateria(), materia);
    }

}
