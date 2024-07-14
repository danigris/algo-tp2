package aed;

import aed.SistemaSIU.CargoDocente;

public class Main2 {

    public static void main(String[] args) {
        InfoMateria[] infoMaterias = new InfoMateria[]{
            new InfoMateria(new ParCarreraMateria[]{
                new ParCarreraMateria("ac", "ad"),
                //new ParCarreraMateria("acc", "add")
            }),
            new InfoMateria(new ParCarreraMateria[]{
                new ParCarreraMateria("ac", "ad2"),
                //new ParCarreraMateria("acc", "add")
            }),
            
        };

        // Crear lista de estudiantes
        String[] estudiantes = {"12345", "67890", "11223"};

        // Inicializar el sistema
        SistemaSIU sistema = new SistemaSIU(infoMaterias, estudiantes);

        // Imprimir toda la información del sistema (lista de carreras, con lista materias de cada una)
        System.out.println(sistema.toString());

        /* // Inscribir estudiantes
        sistema.inscribir("12345","Ciencias de Datos", "Algoritmos3");
        sistema.inscribir("67890","Ciencias de la Computación", "Técnicas de Diseño de Algoritmos");
         */

        // Imprimir información de cada materia en cada carrera
        for (String nombreCarrera : sistema.carreras()) {
            Carrera carrera = sistema.getCarrera(nombreCarrera);
            for (String nombreMateria : carrera.getMaterias().claves()) {
                Materia materia = carrera.getMaterias().obtener(nombreMateria);
                System.out.println(materia.toString());
            }
        }

        sistema.cerrarMateria("ad","ac");

        System.out.println("Despues de borrar");

        // Imprimir toda la información del sistema (lista de carreras, con lista materias de cada una)
        System.out.println(sistema.toString());

        for (String nombreCarrera : sistema.carreras()) {
            Carrera carrera = sistema.getCarrera(nombreCarrera);
            for (String nombreMateria : carrera.getMaterias().claves()) {
                Materia materia = carrera.getMaterias().obtener(nombreMateria);
                System.out.println(materia.toString());
            }
        }
    }
}
