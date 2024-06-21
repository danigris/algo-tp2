package aed;

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
