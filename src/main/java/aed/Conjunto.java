package aed;

/**
 * Interfaz para un Conjunto.
 * 
 * Invariante de Representación:
 * - No duplicados: Un conjunto no contiene elementos duplicados. Cada elemento en el conjunto es único.
 * - No nulidad: Un conjunto no debe contener elementos nulos (null). Todos los elementos en el conjunto deben ser no nulos.
 * - Tamaño no negativo: El tamaño de un conjunto debe ser un número no negativo.
 *
 * @param <T> el tipo de elementos en el conjunto
 *
 */
public interface Conjunto<T> {

    boolean conjuntoVacio();

    boolean pertenece(T elemento);

    void agregar(T elemento);

    void sacar(T elemento);

    int tamaño();

}
