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

        for (InfoMateria equivalentes : infoMaterias) {

            // Extraigo los pares de cada InfoMateria 
            ParCarreraMateria[] pares = equivalentes.getParesCarreraMateria();
            // Instancio el Objeto Cursada para las materias equivalentes, tomando el primer nombre de materia de los pares como el estandar
            String nombreMateriaEstandar = pares[0].getNombreMateria();
            Cursada cursada = new Cursada(nombreMateriaEstandar);

            // Extraigo la data de carrera y nombreMateria de cada ParCarreraMateria
            for (ParCarreraMateria par : pares) {
                String nombreCarrera = par.getCarrera();
                String nombreMateria = par.getNombreMateria();

                // Si la carrera no existe en el sistema, la creo
                Carrera carrera = sistema.obtener(nombreCarrera); // Esto podria reemplazarlo por la clase Carrera; ver bien como
                if (carrera == null) {
                    carrera = new Carrera(nombreCarrera);
                    sistema.definir(nombreCarrera, carrera);
                }
                // Instancio un nuevo objeto Materia y la agrego a la carrera
                Materia materia = new Materia(nombreCarrera, nombreMateria, cursada);
                carrera.agregarMateria(materia);

                // Agrego referencia al diccionario de la carrera que contiene la materia actual
                materia.materiasDeLaCarrera = carrera;

                // Agrego data de equivalentes a la materia (ver si esto es necesario, creo que puede serlo para cerrarMateria)
                materia.cursada.equivalentes = equivalentes;
            }
        }

        for (String estudiante : estudiantes) {
            this.inscripcionesPorEstudiante.definir(estudiante, 0);
        }
    }

    // Métodos adicionales para los tests
    public HashSet<String> getCarreras() {
        return new HashSet<>(sistema.claves());
    }

    public Carrera getCarrera(String nombreCarrera) {
        return sistema.obtener(nombreCarrera);
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
        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);
        materiaActual.cursada.estudiantes.add(estudiante);
        materiaActual.cursada.inscriptos++;

        // Sumo inscripcion al estudiante actual
        Integer totalPrevio = this.inscripcionesPorEstudiante.obtener(estudiante);
        this.inscripcionesPorEstudiante.definir(estudiante, totalPrevio + 1);

    }

    public int inscriptos(String materia, String carrera) {
        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);

        return materiaActual.cursada.inscriptos;
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
