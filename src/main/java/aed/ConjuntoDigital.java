package aed;

import java.util.ArrayList;

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

}
