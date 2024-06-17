package aed;

public class Carrera {

    String nombreCarrera;
    DiccionarioHashMap<String, Materia> materias;

    public Carrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
        this.materias = new DiccionarioHashMap<>();
    }

    public String getNombreCarrera() {
        return this.nombreCarrera;
    }

    public DiccionarioHashMap<String, Materia> getMaterias() {
        return this.materias;
    }

    public void agregarMateria(Materia materia) {
        materias.definir(materia.getNombreMateria(), materia);
    }

}
