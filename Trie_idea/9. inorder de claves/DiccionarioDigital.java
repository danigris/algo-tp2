import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class DiccionarioDigital {//implements Diccionario <K,V> {

    public final TrieNode root;
    public Integer elementos;
    public static final int R = 256; // extended ASCII  // ver si necesitamos todos

    public class TrieNode<T> {//} extends Comparable<T>> innecesario
        T valor;
        boolean end;
        ArrayList<TrieNode<T>> child;

        public TrieNode(T valor) {
            this.child = new ArrayList<>(R);
            for (int i = 0; i < R; i++) {   // inicializar en null, si no no funciona porque length = 0
                child.add(null);
            }
            this.end = false;
            //this.valor = null;               //o puedo poner
            this.valor = valor;
        }

        public T getValue() {
                return this.valor;
        }
        
        public void addChild(TrieNode<T> child) {
                this.child.add(child);
        }

        //public List<TrieNode<T>> getChildren() {
        //        return child;
        //
        //}
    }

    public DiccionarioDigital(){
        this.elementos = 0;
        root = new TrieNode(null);
    }
    public boolean diccionarioVacio() {
        return (this.elementos == 0);
    }


    public <T> void definir (String word ,T v ) {   // acá es donde necesito poner un tipo paramétrico

        TrieNode currentNode = this.root;
        for (int i = 0; i < word.length(); i++) {
            TrieNode node = (TrieNode) currentNode.child.get(word.charAt(i));
            //TrieNode node = currentNode.child.get(word.charAt(i) - 0);
            if (node == null) {
                node = new TrieNode(null);
                currentNode.child.set(word.charAt(i), node);
            }
            currentNode = node;
        }
        if (currentNode.end == false) { // la clave NO estaba en el dic
            currentNode.end = true;
            currentNode.valor = v;
            this.elementos++;
        } else {
            currentNode.valor = v;  // solo redefino su valor
        }
    }

    public boolean esta(String word) {
        TrieNode currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = (TrieNode) currentNode.child.get(ch);
            if (node == null) {
                return false;
            }
            currentNode = node;
        }
        return currentNode.end;
    }




    public boolean borrar(String word) {
        TrieNode currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = (TrieNode) currentNode.child.get(ch);
            if (node == null) {
                return false;
            }
            currentNode = node;
        }
        if (currentNode.end) {
            currentNode.end = false;
            this.elementos = this.elementos - 1;
            return true;
        }
        return false;
    }

    public List<String> listaClaves() {
        List<String> list = new ArrayList<>();
        lista(root, 0, "", list);
        return list;
    }
    private void lista(TrieNode currentNode, int id, String prefijo, List<String> list) {
        if(currentNode==null) return;
        for(int i=0; i<R; i++) {
            TrieNode node = (TrieNode) currentNode.child.get(i);
            if(node!=null) {
                String res = prefijo + (char)i;
                if(node.end) list.add(res);
                lista(node, i, res, list);
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
        return word.matches("^[a-z]+$");
    }

    public static void main(String[] args) {
        DiccionarioDigital obj = new DiccionarioDigital();
        DiccionarioDigital dani = new DiccionarioDigital ();
        dani.definir("hola", "hola");
        String word;
        @SuppressWarnings("resource") Scanner scan = new Scanner(System.in);
        sop("string should contain only a-z character for all operation");
        while (true) {
            sop("1. definir\n2. esta \n3. Delete\n4. diccionarioVacio\n5. inorder de claves\n6.Quit");
            try {
                int t = scan.nextInt();
                switch (t) {
                case 1:
                    word = scan.next();
                    if (isValid(word)) {
                        obj.definir(word, dani );      // atención que acá metí un parametro string
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
