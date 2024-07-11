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
        nuevoSistema(infoMaterias); // Delegar la inicialización a otro método
    }

    private void nuevoSistema(InfoMateria[] infoMaterias) { /* Leon: falta poner que armar el trie con estudiantes es O(E) pq insertar es O(1)
        podrían hacer el chequeo de si está definida la carrera con el método "esta"
        En la complejidad no es |Mc|, es solo Mc, ya que se accede Mc veces a cada carrera en el trie. */
                
    /* Complejidad
    →esta complejidad implica que tiene que dar de alta cada Carrera (en el Objeto de Clase SIU) 
    y a la vez entrar a cada Carrera y dar de alta cada Materia de esta (en Objeto de Clase Carrera)  
    y a la vez registrar todas las materias equivalentes (en cada Objeto de Clase Materia)
    y la vez registrar los estudiantes (en el Objeto de Clase SIU) 

    →se divide en tres partes
    I) en nuestro diccionario tenemos como claves nombres de carreras, y su valor será el diccionario con claves de nombres de materias; 
    como ambos diccionarios están implementados sobre tries, la complejidad está representada por la longitud del string más largo usado como clave, que en caso de carreras es |c|, y de las materias de esa carrera es |Mc|; 
    y como hay que hacer esta operación para cada materia Mc de todas las carreras c ∈ C, se multiplican las complejidades

    II) al dar de alta una materia m, guardamos en esta la referencia a los nombres n de todas sus materias equivalentes en Nm; 
    esto lo hacemos en un trie de strings de nombres de materias n, para el cual insertar un nombre tiene complejidad proporcional a su longitud, es decir complejidad 𝑂(|n|); 
    y como esto lo hacemos para cada materia m, depende del numero total de materias en M y del largo de cada uno de sus nombres

    III) E es la cantidad total de estudiantes en todas las carreras de grado, y al no estar esta cantidad acotada, como los guardamos en un vector de strings cuando los vamos registrando, registrar E estudiantes implica realizar E inserciones, lo que da cuenta de la complejidad lineal de la operación
    */
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
        // Complejidad: Recorrer el trie de la Carrera y de la materia en las 2 primeras líneas. El resto tiene complejidad O(1)
        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);
        materiaActual.cursada.estudiantes.add(estudiante);
        materiaActual.cursada.inscriptos++;

        // Sumo inscripcion al estudiante actual
        Integer totalPrevio = this.inscripcionesPorEstudiante.obtener(estudiante);
        this.inscripcionesPorEstudiante.definir(estudiante, totalPrevio + 1);

    }

    public int inscriptos(String materia, String carrera) {
        // Complejidad: Recorrer el trie de la Carrera y de la materia en las 2 primeras líneas. 
        // El resto son operaciones con complejidad O(1)
        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);

        return materiaActual.cursada.inscriptos;
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia) { 
        // Complejidad: Recorrer el trie de la Carrera y de la materia en las 2 primeras líneas.
        // Como la cantidad de cargos docentes esta acotada (4) encontrar la PosicionDelCargo es O(1)
        // El resto son operaciones con complejidad O(1)  

        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);

        int PosicionDelCargo = PosicionDelCargo(cargo); // De CargoDocente a int para usar set
        
        if (!(PosicionDelCargo == -1)) { // Si el cargo no es incorrecto
            int actualesEnElCargo = materiaActual.cursada.docentes.get(PosicionDelCargo); // Obtengo cuantos hay en el cargo
            materiaActual.cursada.docentes.set(PosicionDelCargo,actualesEnElCargo+1); // Le sumo 1 al Cargo
        }
    }

    private int PosicionDelCargo(CargoDocente cargo) { // De CargoDocente a su indíce del Array
        int res;
        switch (cargo) {
            case AY2:
                res = 0;
                break;
            case AY1:
                res = 1;
                break;
            case JTP:
                res = 2;
                break;
            case PROF:
                res = 3;
                break;
            default:
                res = -1; 
                break;
        }
        return res;
    }
    

    public int[] plantelDocente(String materia, String carrera) {
        // Complejidad: Recorrer el trie de la Carrera y de la materia en las 2 primeras líneas.
        // Como la cantidad de cargos docentes esta acotada (4) encontrar la PosicionDelCargo es O(1)
        // El resto son operaciones con complejidad O(1)  

        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);

        int[] res = new int[4]; // int[] con los 4 cargos
        for (int i = 0; i < 4; i++) {
            res[i] = materiaActual.cursada.docentes.get(3-i); // int[] res se ordena al revés que ArrayList<Integer> docentes;
        }

        return res;
    }

    public void cerrarMateria(String materia, String carrera) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public boolean excedeCupo(String materia, String carrera) {
        // Complejidad: Recorrer el trie de la Carrera y de la materia en las 2 primeras líneas.
        // Como la cantidad de cargos docentes esta acotada (4) encontrar el cargo que limita el cupo es O(1)
        // El resto son operaciones con complejidad O(1)  
        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);
        cupo(materiaActual);

        if (materiaActual.cursada.cupo < materiaActual.cursada.estudiantes.size()) {
            return true;
        }
        return false;
    }

    public int cupo (Materia materiaActual) {
        int cupoPorAY2 = 30 * materiaActual.cursada.docentes.get(0);
        int cupoPorAY1 = 20 * materiaActual.cursada.docentes.get(1);
        int cupoPorJTP = 100 * materiaActual.cursada.docentes.get(2);
        int cupoPorPROF = 250 * materiaActual.cursada.docentes.get(3);
        int cupo = Math.min(Math.min(cupoPorAY1,cupoPorAY2),Math.min(cupoPorJTP,cupoPorPROF));
        materiaActual.cursada.cupo = cupo;
        return materiaActual.cursada.cupo;
    }

    public String[] carreras() {
        /*
        Idea, lo mismo para el de abajo:
    
        String[] res = []

        for (carrera in sistema.claves) {
            res.append(carrera)
        }
        
        return res
        */
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public String[] materias(String carrera) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public int materiasInscriptas(String estudiante) {
        // Complejidad: Recordando que inscripcionesPorEstudiante es un Diccionario Digital, 
        // como el largo de la libreta universitaria esta acotada (6) encontrar el cargo que limita el cupo es O(1)
        return this.inscripcionesPorEstudiante.obtener(estudiante);
    }
}
