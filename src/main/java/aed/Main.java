package aed;

import aed.SistemaSIU.CargoDocente;

public class Main {

    public static void main(String[] args) {
        InfoMateria[] infoMaterias = new InfoMateria[]{
            new InfoMateria(new ParCarreraMateria[]{
                new ParCarreraMateria("Ciencias de la Computación", "Intro a la Programación"),
                new ParCarreraMateria("Ciencias de Datos", "Algoritmos1")
            }),
            new InfoMateria(new ParCarreraMateria[]{
                new ParCarreraMateria("Ciencias de la Computación", "Algoritmos"),
                new ParCarreraMateria("Ciencias de Datos", "Algoritmos2")
            }),
            new InfoMateria(new ParCarreraMateria[]{
                new ParCarreraMateria("Ciencias de la Computación", "Técnicas de Diseño de Algoritmos"),
                new ParCarreraMateria("Ciencias de Datos", "Algoritmos3")
            }),
            new InfoMateria(new ParCarreraMateria[]{
                new ParCarreraMateria("Ciencias de la Computación", "Análisis I"),
                new ParCarreraMateria("Ciencias de Datos", "Análisis I"),
                new ParCarreraMateria("Ciencias Físicas", "Matemática 1"),
                new ParCarreraMateria("Ciencias Químicas", "Análisis Matemático I"),
                new ParCarreraMateria("Ciencias Matemáticas", "Análisis I")
            }),
            new InfoMateria(new ParCarreraMateria[]{
                new ParCarreraMateria("Ciencias Biológicas", "Química General e Inorgánica 1"),
                new ParCarreraMateria("Ciencias Químicas", "Química General")
            }),
            new InfoMateria(new ParCarreraMateria[]{
                new ParCarreraMateria("Ciencias Matemáticas", "Análisis II"),
                new ParCarreraMateria("Ciencias de Datos", "Análisis II"),
                new ParCarreraMateria("Ciencias Físicas", "Matemática 3"),
                new ParCarreraMateria("Ciencias Químicas", "Análisis Matemático II")
            })
        };

        // Crear lista de estudiantes
        String[] estudiantes = {"12345", "67890", "11223"};

        // Inicializar el sistema
        SistemaSIU sistema = new SistemaSIU(infoMaterias, estudiantes);

        // Imprimir toda la información del sistema (lista de carreras, con lista materias de cada una)
        System.out.println(sistema.toString());

        // Inscribir estudiantes
        sistema.inscribir("12345","Ciencias de Datos", "Algoritmos3");
        sistema.inscribir("67890","Ciencias de la Computación", "Técnicas de Diseño de Algoritmos");

        // Agregar Docentes
        sistema.agregarDocente(CargoDocente.AY2,"Ciencias de Datos", "Algoritmos3");
        sistema.agregarDocente(CargoDocente.AY2,"Ciencias de la Computación", "Técnicas de Diseño de Algoritmos");
        sistema.agregarDocente(CargoDocente.AY1,"Ciencias de Datos", "Algoritmos3");
        sistema.agregarDocente(CargoDocente.AY1,"Ciencias de Datos", "Algoritmos3");
        sistema.agregarDocente(CargoDocente.JTP,"Ciencias de Datos", "Algoritmos3");
        sistema.agregarDocente(CargoDocente.PROF,"Ciencias de Datos", "Algoritmos3");

        // Calcula cupos de cada materia en cada carrera
        for (String nombreCarrera : sistema.carreras()) {
            Carrera carrera = sistema.getCarrera(nombreCarrera);
            for (String nombreMateria : carrera.getMaterias().claves()) {
                Materia materia = carrera.getMaterias().obtener(nombreMateria);
                sistema.cupo(materia);
            }
        }

        // Imprimir información de cada materia en cada carrera
        for (String nombreCarrera : sistema.carreras()) {
            Carrera carrera = sistema.getCarrera(nombreCarrera);
            for (String nombreMateria : carrera.getMaterias().claves()) {
                Materia materia = carrera.getMaterias().obtener(nombreMateria);
                System.out.println(materia.toString());
            }
        }
    }
}
