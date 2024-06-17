package aed;

public class Main {
    public static void main(String[] args) {
        InfoMateria[] infoMaterias = new InfoMateria[] {
            new InfoMateria(new ParCarreraMateria[] {
                new ParCarreraMateria("Ciencias de la Computación", "Intro a la Programación"),
                new ParCarreraMateria("Ciencias de Datos", "Algoritmos1")
            }),
            new InfoMateria(new ParCarreraMateria[] {
                new ParCarreraMateria("Ciencias de la Computación", "Algoritmos"),
                new ParCarreraMateria("Ciencias de Datos", "Algoritmos2")
            }),
            new InfoMateria(new ParCarreraMateria[] {
                new ParCarreraMateria("Ciencias de la Computación", "Técnicas de Diseño de Algoritmos"),
                new ParCarreraMateria("Ciencias de Datos", "Algoritmos3")
            }),
            new InfoMateria(new ParCarreraMateria[] {
                new ParCarreraMateria("Ciencias de la Computación", "Análisis I"),
                new ParCarreraMateria("Ciencias de Datos", "Análisis I"),
                new ParCarreraMateria("Ciencias Físicas", "Matemática 1"),
                new ParCarreraMateria("Ciencias Químicas", "Análisis Matemático I"),
                new ParCarreraMateria("Ciencias Matemáticas", "Análisis I")
            }),
            new InfoMateria(new ParCarreraMateria[] {
                new ParCarreraMateria("Ciencias Biológicas", "Química General e Inorgánica 1"),
                new ParCarreraMateria("Ciencias Químicas", "Química General")
            }),
            new InfoMateria(new ParCarreraMateria[] {
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

        // Imprimir toda la información del sistema
        System.out.println(sistema.toString());
    }
}
