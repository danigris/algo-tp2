package aed;

import java.util.ArrayList;

public class Materia {

    String nombreCarrera;
    String nombreMateria;
    ArrayList<String> equivalentes; // esto tiene que cambiarse a un trie<string> (sin values! es un trie generico)
    ArrayList<String> estudiantes;
    ArrayList<Integer> docentes;
    int cupo;

    public Materia(String nombreCarrera, String nombreMateria) {
        this.nombreCarrera = nombreCarrera;
        this.nombreMateria = nombreMateria;
        this.equivalentes = new ArrayList<>();
        this.estudiantes = new ArrayList<>();
        this.docentes = new ArrayList<>();
        this.cupo = 0;
    }

    // Getters
    public String getNombreMateria() {
        return this.nombreMateria;
    }

    public String getNombreCarrera() {
        return this.nombreCarrera;
    }

    public ArrayList<String> getEquivalentes() {
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

}
