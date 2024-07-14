package aed;

public class Materia {

    String nombreCarrera;
    String nombreMateria;
    Cursada cursada; // Referencia a la data compartida de materias equivalentes por aliasing
    Carrera materiasDeLaCarrera; // Referencia a la carrera que contiene esta materia

    public Materia(String nombreCarrera, String nombreMateria, Cursada cursada) {
        this.nombreCarrera = nombreCarrera;
        this.nombreMateria = nombreMateria;
        this.cursada = cursada;
        this.materiasDeLaCarrera = new Carrera(nombreCarrera);
    }

    // Getters
    public String getNombreMateria() {
        return this.nombreMateria;
    }

    public String getNombreCarrera() {
        return this.nombreCarrera;
    }

    public Cursada getCursada() {
        return this.cursada;
    }   

    public Carrera getMateriasDeLaCarrera() {
        return this.materiasDeLaCarrera;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" \n-Info Clase Materia: \n");
        sb.append("Carrera: ").append(nombreCarrera).append("\nMateria: ").append(nombreMateria).append("\n");
        sb.append(cursada.toString()); 
        return sb.toString();      
    }    

}
