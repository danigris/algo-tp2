package aed;

import java.util.ArrayList;

/**
 * Implementación de un Conjunto utilizando un Trie.
 *
 * Invariante de Representación: 
 * - Raíz no nula: El nodo raíz (root) nunca debe ser nulo.
 * - Nodos hijos inicializados: Cada nodo (TrieNodo) debe tener un ArrayList de
 * nodos hijos (hijo) de tamaño R (256 para el conjunto de caracteres extendido
 * ASCII). Cada entrada en el ArrayList debe ser o bien null o un nodo válido.
 * - Marcadores de fin de palabra: El atributo end de un TrieNodo debe ser true
 * si y solo si ese nodo marca el final de una palabra que ha sido agregada al
 * conjunto.
 * - Contador de elementos correcto: La variable elementos debe reflejar
 * correctamente el número de palabras únicas almacenadas en el trie. Esto
 * significa que elementos debe ser igual al número de nodos en el trie donde
 * end es true.
 * - Integridad de palabras: Todas las palabras representadas en el trie deben
 * estar correctamente formadas y accesibles a través del trie. Es decir, cada
 * palabra agregada al conjunto debe poder ser reconstruida siguiendo los hijos 
 * desde la raíz hasta un nodo con end igual a true.
 */
public class ConjuntoDigital implements Conjunto<String> {

    private final TrieNodo root;
    private Integer elementos;
    private static final int R = 256; // extended ASCII, solo usamos mayus y minus (incluyendo Ññ) y espacios, pero creo va 256 igual

    public ConjuntoDigital() {
        this.root = new TrieNodo();
        this.elementos = 0;
    }

    private class TrieNodo {

        boolean end;
        ArrayList<TrieNodo> hijo;

        private TrieNodo() {
            this.hijo = new ArrayList<>(R);
            for (int i = 0; i < R; i++) {
                hijo.add(null);
            }
            this.end = false;
        }

    }

    @Override
    public boolean conjuntoVacio() {
        return (this.elementos == 0);
    }

    @Override
    public boolean pertenece(String word) {
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

    @Override
    public void agregar(String word) {
        TrieNodo NodoActual = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int indice = (int) ch;
            if (NodoActual.hijo.get(indice) == null) {
                NodoActual.hijo.set(indice, new TrieNodo());
            }
            NodoActual = NodoActual.hijo.get(indice);
        }
        if (!NodoActual.end) {
            NodoActual.end = true;
            elementos++;
        }
    }

    @Override
    public void sacar(String palabra) {
        if (!pertenece(palabra)) {
            return;
        }

        TrieNodo NodoActual = root;
        for (int i = 0; i < palabra.length(); i++) {
            char ch = palabra.charAt(i);
            NodoActual = NodoActual.hijo.get((int) ch);
        }
        if (NodoActual.end) {
            NodoActual.end = false;
            elementos--;
        }
    }

    @Override
    public int tamaño() {
        return this.elementos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringHelper(root, new StringBuilder(), sb);
        return sb.toString();
    }

    private void toStringHelper(TrieNodo nodo, StringBuilder prefijo, StringBuilder sb) {
        if (nodo == null) {
            return;
        }
        if (nodo.end) {
            sb.append(prefijo.toString()).append(", ");
        }
        for (int i = 0; i < R; i++) {
            TrieNodo hijo = nodo.hijo.get(i);
            if (hijo != null) {
                prefijo.append((char) i);
                toStringHelper(hijo, prefijo, sb);
                prefijo.deleteCharAt(prefijo.length() - 1); // Backtrack
            }
        }
    }

    public ArrayList<String> obtenerElementos() {
        ArrayList<String> elementosList = new ArrayList<>();
        obtenerElementosHelper(root, new StringBuilder(), elementosList);
        return elementosList;
    }

    private void obtenerElementosHelper(TrieNodo nodo, StringBuilder prefijo, ArrayList<String> lista) {
        if (nodo == null) {
            return;
        }
        if (nodo.end) {
            lista.add(prefijo.toString());
        }
        for (int i = 0; i < R; i++) {
            TrieNodo hijo = nodo.hijo.get(i);
            if (hijo != null) {
                prefijo.append((char) i);
                obtenerElementosHelper(hijo, prefijo, lista);
                prefijo.deleteCharAt(prefijo.length() - 1); // Backtrack
            }
        }
    }

}
