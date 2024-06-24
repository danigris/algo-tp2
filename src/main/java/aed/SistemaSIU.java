package aed;

import java.util.HashSet;

public class SistemaSIU {

    private final DiccionarioDigital<String, Carrera> sistema;
    private final String[] estudiantes;
    private final DiccionarioDigital<String, Integer> inscripcionesPorEstudiante;

    enum CargoDocente {
        AY2,
        AY1,
        JTP,
        PROF
    }

    // Métodos TP
    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias) {
        this.estudiantes = libretasUniversitarias;
        this.sistema = new DiccionarioDigital<>();
        this.inscripcionesPorEstudiante = new DiccionarioDigital<>();
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
            DiccionarioDigital<String, Materia> materias = carrera.getMaterias();
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
        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);

        int PosicionDelCargo = PosicionDelCargo(cargo); // De CargoDocente a int para usar set
     
        int actualesEnElCargo = materiaActual.cursada.docentes.get(PosicionDelCargo); // Obtengo cuantos hay en el cargo
        materiaActual.cursada.docentes.set(PosicionDelCargo,actualesEnElCargo+1); // Le sumo 1 al Cargo
    }

    private int PosicionDelCargo (CargoDocente cargo) { // De CargoDocente a su indíce del Array
        int res;
        if (cargo == CargoDocente.AY2) {
            res = 0;
        } else if (cargo == CargoDocente.AY1) {
            res = 1;
        } else if (cargo == CargoDocente.JTP) {
            res = 2;
        } else { // if (cargo == CargoDocente.PROF)
            res = 3;
        }
        return res;
    }

    public int[] plantelDocente(String materia, String carrera) {
        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);

        int[] res = new int[4]; //int[] con los 4 cargos
        for (int i = 0; i < 4; i++) {
            res[i] = materiaActual.cursada.docentes.get(3-i); // int[] res se ordena al revés que ArrayList<Integer> docentes;
        }

        return res;
    }

    public void cerrarMateria(String materia, String carrera) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public boolean excedeCupo(String materia, String carrera) {
        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);

        int cupo = cupo(materiaActual);

        if (cupo < materiaActual.cursada.estudiantes.size()) {
            return true;
        }
        return false;
    }

    public int cupo (Materia materiaActual) {
        int cupoPorAY2 = 30 * materiaActual.cursada.docentes.get(0);
        int cupoPorAY1 = 20 * materiaActual.cursada.docentes.get(1);
        int cupoPorJTP = 100 * materiaActual.cursada.docentes.get(2);
        int cupoPorPROF = 250 * materiaActual.cursada.docentes.get(3);
        return Math.min(Math.min(cupoPorAY1,cupoPorAY2),Math.min(cupoPorJTP,cupoPorPROF));
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
