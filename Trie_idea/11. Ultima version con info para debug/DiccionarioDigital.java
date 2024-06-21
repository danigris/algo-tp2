import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class DiccionarioDigital <K,V>  {//implements Diccionario <K,V> {

    private final TrieNodo root;
    private Integer elementos;
    private static final int R = 256; // extended ASCII  // ver si necesitamos todos

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
        
        //private void agregarhijo(TrieNodo<V> hijo) {
        //        this.hijo.add(hijo);
        //}

    }

    public DiccionarioDigital(){
        this.elementos = 0;
        root = new TrieNodo(null);
    }
    public boolean diccionarioVacio() {
        return (this.elementos == 0);
    }


    public void definir (String word ,V v ) {   // acá es donde necesito poner un tipo paramétrico

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
        if (NodoActual.end == false) { // la clave NO estaba en el dic
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

    public  V obtener(String word) {       // ver como hago para que este objeto sea V
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

    public boolean borrar(String word) {
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
    public Integer tamaño(){
        return this.elementos;
    }

    public List<String> listaClaves() {
        List<String> list = new ArrayList<>();
        lista(root, "", list);
        return list;
    }
    private void lista(TrieNodo NodoActual, String prefijo, List<String> list) {
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


    public static void sop(String print) {
        System.out.println(print);
    }

    /**
     * Regex to check if word contains only a-z character
     */
    public static boolean isValid(String word) {
        return true; //word.matches("^[a-z]+$");
    }

    public static void main(String[] args) {
        DiccionarioDigital obj = new DiccionarioDigital();
        DiccionarioDigital dani = new DiccionarioDigital ();
        DiccionarioDigital dani2 = new DiccionarioDigital ();

        int[] listaDeNumeros = new int[8];
        for (int i = 0; i < listaDeNumeros.length; i++) {
            listaDeNumeros[i] = i;
        }

        dani.definir("z", listaDeNumeros); // pongo z para que aparezca al toque
        dani2.definir("w", listaDeNumeros);
        String word;
        @SuppressWarnings("resource") Scanner scan = new Scanner(System.in);
        sop("string should contain only a-z character for all operation");
        while (true) {
            sop("1. definir\n2. esta \n3. Delete\n4. diccionarioVacio\n5. inorder de claves\n6.tamaño\n7.obtener\n0.Quit");
            try {
                int t = scan.nextInt();
                switch (t) {
                case 1:
                    word = scan.next();
                    if (isValid(word))  {
                        if (obj.elementos == 0) {
                            obj.definir(word, dani);    // estoy metiendo como parametro dos arreglos, va bien. 
                        } else {
                            obj.definir(word, dani2);
                        }
                    } else {
                        sop("Invalid string: allowed only a-z");
                    }
                    break;
                case 2:
                    word = scan.next();
                    boolean resS = false;
                    System.out.println ("cantidad de elementos ");
                    System.out.println (obj.elementos);
                    if (isValid(word)) {
                        resS = obj.esta(word);
                    } else {
                        sop("Invalid string: allowed only a-z");
                    }
                    if (resS) {
                        sop("word found");
                    } else {
                        sop("word not found");
                    }
                    break;
                case 3:
                    word = scan.next();
                    boolean resD = false;
                    if (isValid(word)) {
                        resD = obj.borrar(word);
                    } else {
                        sop("Invalid string: allowed only a-z");
                    }
                    if (resD) {
                        sop("word got deleted successfully");
                    } else {
                        sop("word not found");
                    }
                    break;
                case 4:
                    if (obj.diccionarioVacio()) {
                        sop("El diccionario está vacío");
                    } else {
                        sop("Diccionario NO VACÍO");
                    }
                    break;

                case 5:
                    System.out.println(obj.listaClaves());
                    break;

                case 6:
                    System.out.println(obj.tamaño());
                    break;

                    case 7: //obtener

                        word = scan.next();
                        Object test = obj.obtener(word);
                        break;

                case 0:
                    sop("Quit successfully");
                    System.exit(1);
                    break;
                default:
                    sop("Input int from 1-4");
                    break;
                }
            } catch (Exception e) {
                String badInput = scan.next();
                sop("This is bad input: " + badInput);
            }
        }
    }
}
