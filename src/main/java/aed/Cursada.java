package aed;

import java.util.ArrayList;

/**
 * Clase que representa la cursada de una materia.
 *
 * Invariante de Representación: 
 * - El nombre estándar de la materia no puede ser nulo o un string vacío. 
 * - El conjunto de equivalentes debe ser una instancia no nula de ConjuntoDigital.
 * - La lista de estudiantes debe ser una instancia no nula de ArrayList de Strings y
 *   sus elementos deben tener largo fijo (son las libretas universitarias). 
 * - La lista de docentes debe ser una instancia no nula de ArrayList de Integers y
 *   tener siempre cuatro elementos no negativos.
 * - El cupo debe ser un entero no negativo. 
 * - El ńumero de inscriptos debe ser un entero no negativo y no puede
 *   ser mayor que el cupo.
 * 
 */
public class Cursada {

    String nombreMateriaEstandar;
    ConjuntoDigital equivalentes;
    ArrayList<String> estudiantes;
    ArrayList<Integer> docentes;
    int cupo;
    int inscriptos;

    public Cursada(String nombreMateria) {
        this.nombreMateriaEstandar = nombreMateria;
        this.equivalentes = new ConjuntoDigital();
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

    public ConjuntoDigital getEquivalentes() {
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
        sb.append("Equivalentes: ").append(equivalentes.toString()).append("\n");
        sb.append("\nEstudiantes: ").append(estudiantes).append("\n");
        sb.append("Docentes: ").append(docentes).append("\n");
        sb.append("Cupo: ").append(cupo).append("\n");
        sb.append("Inscriptos: ").append(inscriptos).append("\n");
        return sb.toString();
    }

}
