package aed;

public interface Conjunto<T> {

    boolean conjuntoVacio();

    boolean pertenece(T elemento);

    void agregar(T elemento);

    void sacar(T elemento);

    int tamaño();

}
