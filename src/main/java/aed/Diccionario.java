package aed;

public interface Diccionario<K,V> {

    boolean diccionarioVacio(); // verdadero si no hay ninguna clave definida
     
    boolean esta(K clave); // verdadero si la clave está definida
    
    void definir(K clave, V valor); 
    
    V obtener(K clave);
    
    void borrar(K clave);
    
    int tamaño();
    
    /**
     * Imprime el conjunto,  ver si es necesario
     *
     */
    @Override
    String toString();

}
