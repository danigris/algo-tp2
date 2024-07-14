package aed;


public class Main2 {

    public static void main(String[] args) {
        InfoMateria[] infoMaterias = new InfoMateria[]{
            new InfoMateria(new ParCarreraMateria[]{
                new ParCarreraMateria("ac", "ad2"),
                new ParCarreraMateria("acc", "add2")
            }),
            new InfoMateria(new ParCarreraMateria[]{
                new ParCarreraMateria("ac", "ae"),
                new ParCarreraMateria("acc", "ae")
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
                //System.out.println(materia.toString());
            }
        }

        // sistema.cerrarMateria("ad","ac"); // Tambien deberia borrar ("ad2","acc")
        sistema.cerrarMateria("ae","ac"); // Tambien deberia borrar ("ae","acc")
        System.out.println("Despues de borrar");

        System.out.println(sistema.toString());
        for (String nombreCarrera : sistema.carreras()) {
            Carrera carrera = sistema.getCarrera(nombreCarrera);
            for (String nombreMateria : carrera.getMaterias().claves()) {
                Materia materia = carrera.getMaterias().obtener(nombreMateria);
                //System.out.println(materia.toString());
            }
        }
    }
}
