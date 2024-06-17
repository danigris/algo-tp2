package aed;

import java.util.HashSet;

public class SistemaSIU {

    private final DiccionarioHashMap<String, Carrera> sistema;
    private final String[] estudiantes;
    private final DiccionarioHashMap<String, Integer> inscripcionesPorEstudiante;

    enum CargoDocente {
        AY2,
        AY1,
        JTP,
        PROF
    }

    // Métodos TP
    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias) {
        this.estudiantes = libretasUniversitarias;
        this.sistema = new DiccionarioHashMap<>();
        this.inscripcionesPorEstudiante = new DiccionarioHashMap<>();
        inicializarSistema(infoMaterias); // Delegar la inicialización a otro método
    }

    private void inicializarSistema(InfoMateria[] infoMaterias) {

        for (InfoMateria info : infoMaterias) {
            // Extraigo los pares de cada InfoMateria y la data de carrera y nombreMateria de cada ParCarreraMateria
            ParCarreraMateria[] pares = info.getParesCarreraMateria();
            for (ParCarreraMateria par : pares) {
                String nombreCarrera = par.getCarrera();
                String nombreMateria = par.getNombreMateria();

                // Si la carrera no existe en el sistema, la creo
                Carrera carrera = sistema.obtener(nombreCarrera); // Esto podria reemplazarlo por la clase Carrera; ver bien como
                if (carrera == null) {
                    carrera = new Carrera(nombreCarrera);
                    sistema.definir(nombreCarrera, carrera);
                }
                // Instancio un nuevo objeto Materia con la carrera actual y el nombre de la materia
                Materia materia = new Materia(nombreCarrera, nombreMateria);
                // Agrego la materia a la carrera
                carrera.agregarMateria(materia);

            }
        }

        for (String estudiante : estudiantes) {
            inscripcionesPorEstudiante.definir(estudiante, 0);
        }
    }

    // Métodos adicionales para los tests
    public HashSet<String> getCarreras() {
        return new HashSet<>(sistema.claves());
    }

    public HashSet<String> getMateriasPorCarrera(String nombreCarrera) {
        Carrera carrera = sistema.obtener(nombreCarrera);
        return carrera != null ? new HashSet<>(carrera.getMaterias().claves()) : new HashSet<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String nombreCarrera : sistema.claves()) {
            Carrera carrera = sistema.obtener(nombreCarrera);
            sb.append("Carrera: ").append(carrera.getNombreCarrera()).append("\n");
            DiccionarioHashMap<String, Materia> materias = carrera.getMaterias();
            for (String nombreMateria : materias.claves()) {
                Materia materia = materias.obtener(nombreMateria);
                sb.append("\tMateria: ").append(nombreMateria).append("\n");
            }
        }
        return sb.toString();
    }

    public void inscribir(String estudiante, String carrera, String materia) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public int[] plantelDocente(String materia, String carrera) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public void cerrarMateria(String materia, String carrera) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public int inscriptos(String materia, String carrera) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public boolean excedeCupo(String materia, String carrera) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public String[] carreras() {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public String[] materias(String carrera) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public int materiasInscriptas(String estudiante) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }
}
