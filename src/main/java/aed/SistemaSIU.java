package aed;


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

    /**
     * Inicializa el sistema SIU Guaraní con la información de materias y
     * estudiantes.
     *
     * Complejidad: 
     * Esta operación implica dar de alta cada carrera, entrar a cada carrera y
     * dar de alta cada materia, registrar todas las materias equivalentes y
     * registrar los estudiantes.
     * 
     * I) Dar de alta cada carrera y cada materia dentro de ella implica acceder
     * a los tries de carreras y materias: 
     * - Para cada carrera c en C, la complejidad de acceder a la carrera es O(|c|). 
     * - Para cada materia m en Mc, la complejidad de acceder a la materia es O(|m|). 
     * - Por lo tanto, la complejidad de esta parte es O(Σ (|c| * |Mc|)) 
     * para todos los c en C, siendo Mc el conjunto de materias de la carrera y 
     * |Mc| la cantidad de materias de esa carrera.
     *
     * II) Registrar materias equivalentes:  
     * - Insertar un nombre de materia (n) en un trie de strings tiene
     * complejidad O(|n|). 
     * - Como esto se hace para cada materia m en M con todas sus materias
     * equivalentes, la complejidad es O(Σ |n|) para todos los n en N_m 
     * (conjunto de nombres de la materia m) y m en M (conjunto de materias).
     *
     * III) Registrar estudiantes:
     * - Insertar cada estudiante en un trie es O(1) debido a la longitud fija 
     * de las libretas universitarias. 
     * - Registrar E estudiantes implica una complejidad O(E).
     *
     * @param infoMaterias un array de objetos InfoMateria que contiene la
     * información de las materias y sus equivalentes
     */
    private void nuevoSistema(InfoMateria[] infoMaterias) {       

        for (InfoMateria infoMateria : infoMaterias) {
            // Extraigo los pares de cada infoMateria 
            ParCarreraMateria[] pares = infoMateria.getParesCarreraMateria();

            // Instancio el Objeto Cursada para las materias equivalentes, tomando el primer nombre de materia de los pares como el estandar
            String nombreMateriaEstandar = pares[0].getNombreMateria();
            Cursada cursada = new Cursada(nombreMateriaEstandar);

            // Extraigo la data de carrera y nombreMateria de cada par de ParCarreraMateria
            for (ParCarreraMateria par : pares) {
                String nombreCarrera = par.getCarrera();
                String nombreMateria = par.getNombreMateria();

                // Si la carrera no existe en el sistema, la creo
                Carrera carrera = sistema.obtener(nombreCarrera);
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
                materia.cursada.equivalentes = infoMateria;
            }
        }

        for (String estudiante : estudiantes) {
            this.inscripcionesPorEstudiante.definir(estudiante, 0);
        }
    }

    // Métodos adicionales para los tests
    public Carrera getCarrera(String nombreCarrera) {
        return sistema.obtener(nombreCarrera);
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

    /**
     * Este método inscribe un estudiante en una materia específica dentro de 
     * una carrera específica.
     *
     * Complejidad: O(|c| + |m|), donde |c| es la longitud del nombre de la carrera 
     * y |m| es la longitud del nombre de la materia.
     * - Recorrer el trie de la Carrera: O(|c|)
     * - Recorrer el trie de la Materia: O(|m|)
     * - Añadir el estudiante a la lista de estudiantes de la materia: O(1)
     * - Actualizar el número de inscriptos: O(1)
     * - Actualizar el número de inscripciones del estudiante: O(1)
     *
     * @param estudiante la posición del estudiante en el array de estudiantes
     * @param carrera el nombre de la carrera a la cual pertenece la materia
     * @param materia el nombre de la materia en la cual se desea inscribir al estudiante
     */
    public void inscribir(String estudiante, String carrera, String materia) {
       
        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);
        materiaActual.cursada.estudiantes.add(estudiante);
        materiaActual.cursada.inscriptos++;

        // Sumo inscripcion al estudiante actual
        Integer totalPrevio = this.inscripcionesPorEstudiante.obtener(estudiante);
        this.inscripcionesPorEstudiante.definir(estudiante, totalPrevio + 1);

    }

    /**
     * Este método devuelve la cantidad de inscriptos en una materia específica 
     * dentro de una carrera específica.
     *
     * Complejidad: O(|c| + |m|), donde |c| es la longitud del nombre de la 
     * carrera y |m| es la longitud del nombre de la materia.
     * - Recorrer el trie de la Carrera: O(|c|)
     * - Recorrer el trie de la Materia: O(|m|)
     * - El resto son operaciones con complejidad O(1)
     *
     * @param materia el nombre de la materia para la cual se desea obtener 
     * la cantidad de inscriptos
     * @param carrera el nombre de la carrera a la cual pertenece la materia
     * @return la cantidad de inscriptos en la materia especificada
     */
    public int inscriptos(String materia, String carrera) {
        
        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);

        return materiaActual.cursada.inscriptos;
    }

    /**
     * Este método agrega un docente a un cargo específico dentro de una materia 
     * en una carrera específica.
     *
     * Complejidad: O(|c| + |m|), donde |c| es la longitud del nombre de la 
     * carrera y |m| es la longitud del nombre de la materia.
     * - Recorrer el trie de la Carrera: O(|c|)
     * - Recorrer el trie de la Materia: O(|m|)
     * - Como la cantidad de cargos docentes está acotada (4), 
     * encontrar la PosicionDelCargo es O(1)
     * - El resto son operaciones con complejidad O(1)
     *
     * @param cargo el cargo docente que se desea agregar
     * @param carrera el nombre de la carrera a la cual pertenece la materia
     * @param materia el nombre de la materia para la cual se desea agregar el docente
     */
    public void agregarDocente(CargoDocente cargo, String carrera, String materia) {

        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);

        int PosicionDelCargo = PosicionDelCargo(cargo); // De CargoDocente a int para usar set

        if (!(PosicionDelCargo == -1)) { // Si el cargo no es incorrecto
            int actualesEnElCargo = materiaActual.cursada.docentes.get(PosicionDelCargo); // Obtengo cuantos hay en el cargo
            materiaActual.cursada.docentes.set(PosicionDelCargo, actualesEnElCargo + 1); // Le sumo 1 al Cargo
        }
    }

    private int PosicionDelCargo(CargoDocente cargo) {
        // De CargoDocente a su indíce del Array
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

    /**
     * Este método devuelve un array de enteros que representa el plantel 
     * docente de una materia específica dentro de una carrera específica.
     *
     * Complejidad: O(|c| + |m|), donde |c| es la longitud del nombre de la 
     * carrera y |m| es la longitud del nombre de la materia.
     * - Recorrer el trie de la Carrera: O(|c|)
     * - Recorrer el trie de la Materia: O(|m|)
     * - Como la cantidad de cargos docentes está acotada (4), encontrar la
     * PosicionDelCargo es O(1)
     * - El resto son operaciones con complejidad O(1)
     *
     * @param materia el nombre de la materia para la cual se desea obtener el
     * plantel docente
     * @param carrera el nombre de la carrera a la cual pertenece la materia
     * @return un array de enteros que contiene los 4 cargos docentes ordenados
     * en orden inverso al del ArrayList original
     */
    public int[] plantelDocente(String materia, String carrera) {

        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);

        int[] res = new int[4]; // int[] con los 4 cargos
        for (int i = 0; i < 4; i++) {
            res[i] = materiaActual.cursada.docentes.get(3 - i); // int[] res se ordena al revés que ArrayList<Integer> docentes;
        }

        return res;
    }

    /**
     * Este método cierra una materia y desinscribe a sus estudiantes
     * para cada carrera que la tenga 
     *
     * Complejidad: O(|c| + |m| + Σ |n| + E_m) para todos los n en N_m, 
     * donde |c| es la longitud del nombre de la carrera, |m| es la longitud 
     * del nombre de la materia, Σ |n| es el largo del conjunto de nombres de la materia m
     * y E
     * 
     * - Recorrer el trie de la Carrera: O(|c|) 
     * - Recorrer el trie de la Materia: O(|m|) 
* - Recorrer  O(Σ |n|) !!! falta ver que se recorre
     * - Reducir en 1 la cantidad de inscripciones de los estudiantes que estaban 
     * inscriptos a la materia: O(E_m)
* - El resto son operaciones con complejidad O(1) !!! por ahora no hay ninguna con O(1)
     *
     * @param materia el nombre de la materia
     * @param carrera el nombre de la carrera
     * @return true si la cantidad de estudiantes excede el cupo, false en caso contrario
     */
    public void cerrarMateria(String materia, String carrera) {
        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);

        // Resto inscripcion a cada estudiante
        for (String estudiante : materiaActual.cursada.estudiantes) { // Arraylist
        Integer totalPrevio = this.inscripcionesPorEstudiante.obtener(estudiante);
        this.inscripcionesPorEstudiante.definir(estudiante, totalPrevio - 1);
        }

        // Borra la materia en cada equivalencia


        // Borra la materia en la Carrera de parametro de entrada
        carreraActual.getMaterias().borrar(materia);
    }

    /**
     * Este método verifica si la cantidad de estudiantes inscriptos en una
     * materia excede el cupo disponible.
     *
     * Complejidad: O(|c| + |m|), donde |c| es la longitud del nombre de la 
     * carrera y |m| es la longitud del nombre de la materia. 
     * - Recorrer el trie de la Carrera: O(|c|) 
     * - Recorrer el trie de la Materia: O(|m|) 
     * - La cantidad de cargos docentes está acotada (4), 
     * por lo que encontrar el cargo que limita el cupo es O(1) 
     * - El resto son operaciones con complejidad O(1)
     *
     * @param materia el nombre de la materia
     * @param carrera el nombre de la carrera
     * @return true si la cantidad de estudiantes excede el cupo, false en caso contrario
     */
    public boolean excedeCupo(String materia, String carrera) {

        Carrera carreraActual = sistema.obtener(carrera);
        Materia materiaActual = carreraActual.getMaterias().obtener(materia);
        cupo(materiaActual);

        return materiaActual.cursada.cupo < materiaActual.cursada.estudiantes.size();
    }

    public int cupo(Materia materiaActual) {
        int cupoPorAY2 = 30 * materiaActual.cursada.docentes.get(0);
        int cupoPorAY1 = 20 * materiaActual.cursada.docentes.get(1);
        int cupoPorJTP = 100 * materiaActual.cursada.docentes.get(2);
        int cupoPorPROF = 250 * materiaActual.cursada.docentes.get(3);
        int cupo = Math.min(Math.min(cupoPorAY1, cupoPorAY2), Math.min(cupoPorJTP, cupoPorPROF));
        materiaActual.cursada.cupo = cupo;
        return materiaActual.cursada.cupo;
    }

    /**
     * Este método devuelve un array de todas las carreras de grado.
     * 
     * Complejidad: O(Σ |c|) para todos los c en C,
     * donde C es el conjunto de todas las carreras y |c| es la longitud de cada
     * carrera.
     *
     * @return un array de strings que contiene todas las carreras, ordenado
     * lexicográficamente
     */
    public String[] carreras() {
        return sistema.claves();
    }

    /**
     * Este método devuelve un array de todas las materias asociadas a una
     * carrera específica.
     *
     * Complejidad: O(|c| + Σ |m_c|) para todos los m_c en M_c, donde |c| es la
     * longitud del nombre de la carrera y Σ |m_c| es la suma de las longitudes de
     * los nombres de las materias en la carrera.
     *
     * @param carrera el nombre de la carrera para la cual se desean obtener las
     * materias
     * @return un array de strings que contiene todas las materias de la carrera
     * especificada, ordenado lexicográficamente
     */
    public String[] materias(String carrera) {
        Carrera carreraActual = sistema.obtener(carrera);
        DiccionarioDigital<String, Materia> diccionarioMaterias = carreraActual.getMaterias();
        String[] listaMaterias = diccionarioMaterias.claves();

        return listaMaterias;
    }

    /**
     * Este método devuelve la cantidad de materias en las que un estudiante 
     * está inscripto.
     *
     * Complejidad: O(1), ya que inscripcionesPorEstudiante es un Diccionario 
     * Digital y la longitud de la libreta universitaria -que representa a cada 
     * estudiante- está acotada a un tamaño fijo (6 caracteres), lo que permite 
     * encontrar el número de inscripciones en tiempo constante.
     *
     * @param estudiante la libreta universitaria del estudiante
     * @return la cantidad de materias en las que el estudiante está inscripto
     */
    public int materiasInscriptas(String estudiante) {
       
        return this.inscripcionesPorEstudiante.obtener(estudiante);
    }
}
