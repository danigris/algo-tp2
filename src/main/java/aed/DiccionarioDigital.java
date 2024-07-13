package aed;

import java.util.ArrayList;

public class DiccionarioDigital<K, V> /* implements Diccionario <K,V> */ {

    private final TrieNodo root;
    private Integer elementos;
    private static final int R = 256; // extended ASCII, solo usamos mayus y minus (incluyendo Ññ) y espacios, pero creo va 256 igual

    private class TrieNodo {//} extends Comparable<T>> innecesario

        V valor;
        boolean end;
        ArrayList<TrieNodo> hijo;

        private TrieNodo(V valor) {
            this.hijo = new ArrayList<>(R);
            for (int i = 0; i < R; i++) {   // inicializar en null, si no no funciona porque length = 0
                hijo.add(null);
            }
            this.end = false;
            //this.valor = null;               //o puedo poner
            this.valor = valor;
        }

        private V obtenerValor() {
            return this.valor;
        }
    }

    public DiccionarioDigital() {
        this.elementos = 0;
        root = new TrieNodo(null);
    }

    public boolean diccionarioVacio() {
        return (this.elementos == 0);
    }

    public void definir(String word, V v) {    // acá es donde necesito poner un tipo paramétrico

        TrieNodo NodoActual = this.root;
        for (int i = 0; i < word.length(); i++) {
            TrieNodo nodo = NodoActual.hijo.get(word.charAt(i));
            //TrieNodo nodo = NodoActual.hijo.get(word.charAt(i) - 0);
            if (nodo == null) {
                nodo = new TrieNodo(null);
                NodoActual.hijo.set(word.charAt(i), nodo);
            }
            NodoActual = nodo;
        }
        if (!NodoActual.end) { // la clave NO estaba en el dic
            NodoActual.end = true;
            NodoActual.valor = v;
            this.elementos++;
        } else {
            NodoActual.valor = v;  // solo redefino su valor
        }
    }

    public boolean esta(String word) {
        TrieNodo NodoActual = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNodo nodo = NodoActual.hijo.get(ch);
            if (nodo == null) {
                return false;
            }
            NodoActual = nodo;
        }
        return NodoActual.end;
    }

    public V obtener(String word) {       // ver como hago para que este objeto sea V
        TrieNodo NodoActual = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNodo nodo =  NodoActual.hijo.get(ch);
            if (nodo == null) {
                return null;
            }
            NodoActual = nodo;
        }
        return NodoActual.obtenerValor();
    }

    public boolean borrar(String word) { // Leon: no mantiene el invariante del mismo (mantiene nodos inutiles)
        TrieNodo NodoActual = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNodo nodo = NodoActual.hijo.get(ch);
            if (nodo == null) {
                return false;
            }
            NodoActual = nodo;
        }
        if (NodoActual.end) {
            NodoActual.end = false;
            this.elementos = this.elementos - 1;
            return true;
        }
        return false;
    }
    public int tamaño(){
        return this.elementos;
    }

    /**
     * Este método devuelve un array de todas las claves almacenadas en el Trie.
     *
     * Complejidad: O(Σ |c|) para todos los c en C, donde C es el conjunto de
     * todas las claves en el Trie y |c| es la longitud de cada clave.
     *
     * @return un array de strings que contiene todas las claves en el Trie,
     * ordenado lexicograficamente
     */
    public String[] claves() {
        ArrayList<String> list = new ArrayList<>();
        lista(root, new StringBuilder(), list);
        String[] claves = list.toArray(new String[list.size()]);
        return claves;
    }

    /**
     * Método recursivo que construye una lista de todas las claves en el Trie a
     * partir del nodo dado.
     *
     * Complejidad: O(Σ |c|) para todos los c en C, donde C es el conjunto de
     * todas las claves en el Trie y |c| es la longitud de cada clave.
     *
     * @param NodoActual el nodo actual del Trie desde el cual se comienza la
     * búsqueda
     * @param prefijo el prefijo acumulado hasta el nodo actual
     * @param list la lista en la que se almacenan las claves encontradas
     */
    private void lista(TrieNodo NodoActual, StringBuilder prefijo, ArrayList<String> list) {
        if (NodoActual == null) {
            return;
        }

        for (int i = 0; i < R; i++) {
            TrieNodo nodo = NodoActual.hijo.get(i);
            if (nodo != null) {
                char caracter = (char) i;
                prefijo.append(caracter);
                if (nodo.end) {
                    list.add(prefijo.toString());
                }
                lista(nodo, prefijo, list);
                prefijo.deleteCharAt(prefijo.length() - 1); // Restaurar el prefijo
            }
        }

    }

}
