package aed;

interface Diccionario<K,V> {

    public boolean diccionarioVacio(); // verdadero si no hay ninguna clave definida
     
    public boolean esta(K clave); // verdadero si la clave está definida
    
    public void definir(K clave, V valor); 
    
    public V obtener(K clave);
    
    public void borrar(K clave);
    
    // no hay definirRapido
    
    public int tamaño();
    
    /**
     * Imprime el conjunto,  ver si es necesario
     *
     */
    // public String toString();

}
