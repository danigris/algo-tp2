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

    
    
    
    
    
    public T minimo();

    /**
     * Devuelve el mayor elemento del conjunto
     *
     */
    public T maximo();

    /**
     * Agrega un elemento al conjunto
     * 
     */
    public void insertar(T elem);

    /**
     * Devuelve verdadero si el elemento pertenece al conjunto
     * 
     */
    public boolean pertenece(T elem);

    /**
     * Elimina el elemento del donjunto
     * 
     */
    public void eliminar(T elem);

    /**
     * Imprime el conjunto
     *
     */
    public String toString();

    /**
     * Retorna un conjunto con los mismos elementos
     * 
     */
    // public ABB<T> copiar();

}
