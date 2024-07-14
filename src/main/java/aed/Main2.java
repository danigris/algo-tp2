package aed;


public class Main2 {

    public static void main(String[] args) {
        InfoMateria[] infoMaterias = new InfoMateria[]{
            new InfoMateria(new ParCarreraMateria[]{
                new ParCarreraMateria("ac", "ad"),
                new ParCarreraMateria("acc", "add")
            }),
            new InfoMateria(new ParCarreraMateria[]{
                new ParCarreraMateria("ac", "ad2"),
                new ParCarreraMateria("acc", "add2")
            }),
        }; 
        String[] estudiantes = {};
        SistemaSIU sistema = new SistemaSIU(infoMaterias, estudiantes);


        System.out.println("Antes de borrar");

        System.out.println(sistema.toString());
        for (String nombreCarrera : sistema.carreras()) {
            Carrera carrera = sistema.getCarrera(nombreCarrera);
            for (String nombreMateria : carrera.getMaterias().claves()) {
                Materia materia = carrera.getMaterias().obtener(nombreMateria);
                System.out.println(materia.toString());
            }
        }

        sistema.cerrarMateria("ad","ac"); // Tambien deberia borrar ("ad2","acc")
        System.out.println("Despues de borrar");

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
