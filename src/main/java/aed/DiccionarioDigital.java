package aed;

import java.util.List;
import java.util.ArrayList;

public class DiccionarioDigital <K,V>   /* implements Diccionario <K,V> */ {

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

    public DiccionarioDigital(){
        this.elementos = 0;
        root = new TrieNodo(null);
    }
    public boolean diccionarioVacio() {
        return (this.elementos == 0);
    }

    public void definir (String word ,V v ) {    // acá es donde necesito poner un tipo paramétrico

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

    public List<String> claves() { // Antes era listaClaves
        List<String> list = new ArrayList<>();
        lista(root, "", list);
        return list;
    }
    private void lista(TrieNodo NodoActual, String prefijo, List<String> list) { /*  Leon: hacen "string + char" para agregar chars al prefijo.
     Haciendo eso la complejidad se va de las manos, ya que en cada iteración se recorre el string de prefijo para agregar el char. 
     Vean de usar un StringBuilder. */
        if(NodoActual==null) return;
        for(int i=0; i<R; i++) {
            TrieNodo nodo = NodoActual.hijo.get(i);
            if(nodo!=null) {
                String res = prefijo + (char)i;
                if(nodo.end) list.add(res);
                lista(nodo, res, list);
            }
        }
    }
}
