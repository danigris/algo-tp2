package aed;

import java.util.ArrayList;

public class Cursada {

    String nombreMateriaEstandar;
    InfoMateria equivalentes;
    ArrayList<String> estudiantes;
    ArrayList<Integer> docentes;
    int cupo;
    int inscriptos;

    public Cursada(String nombreMateria) {
        this.nombreMateriaEstandar = nombreMateria;
        this.equivalentes = new InfoMateria(new ParCarreraMateria[]{}); // Inicialización con un arreglo vacío
        this.estudiantes = new ArrayList<>();
        this.docentes = inicializarDocentes();
        this.cupo = 0;
        this.inscriptos = 0;
    }

    // Método para inicializar la lista de docentes con cuatro ceros
    private ArrayList<Integer> inicializarDocentes() {
        ArrayList<Integer> listaDocentes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            listaDocentes.add(0);
        }
        return listaDocentes;
    }

    // Getters
    public String getNombreEstandarizado() {
        return this.nombreMateriaEstandar;
    }

    public InfoMateria getEquivalentes() {
        return this.equivalentes;
    }

    public ArrayList<String> getEstudiantes() {
        return this.estudiantes;
    }

    public ArrayList<Integer> getDocentes() {
        return this.docentes;
    }

    public int getCupo() {
        return this.cupo;
    }

    public int getInscriptos() {
        return this.inscriptos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n-Info Clase Cursada: \n");
        sb.append("Nombre Materia Estandar: ").append(nombreMateriaEstandar).append("\n");
        sb.append("Equivalentes: ");
        for (ParCarreraMateria par : equivalentes.getParesCarreraMateria()) {
            sb.append(par.getCarrera()).append(" - ").append(par.getNombreMateria()).append(", ");
        }
        sb.append("\nEstudiantes: ").append(estudiantes).append("\n");
        sb.append("Docentes: ").append(docentes).append("\n");
        sb.append("Cupo: ").append(cupo).append("\n");
        sb.append("Inscriptos: ").append(inscriptos).append("\n");
        return sb.toString();
    }

}
