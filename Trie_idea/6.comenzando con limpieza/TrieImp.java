
import java.util.ArrayList;
import java.util.Scanner;

public class TrieImp {
    public final TrieNode root;
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

    public TrieImp() {
        root = new TrieNode(null);
    }

    public <T> void insert(String word ,T v ) {   // acá es donde necesito poner un tipo paramétrico

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
        currentNode.end = true;
        currentNode.valor = v;
    }

    public boolean search(String word) {
        TrieNode currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = (TrieNode) currentNode.child.get(ch - 0);
            if (node == null) {
                return false;
            }
            currentNode = node;
        }
        return currentNode.end;
    }

    public boolean delete(String word) {
        TrieNode currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = (TrieNode) currentNode.child.get(ch - 0);
            if (node == null) {
                return false;
            }
            currentNode = node;
        }
        if (currentNode.end) {
            currentNode.end = false;
            return true;
        }
        return false;
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
        TrieImp obj = new TrieImp();
        TrieImp dani = new TrieImp ();
        dani.insert("hola", "hola");
        String word;
        @SuppressWarnings("resource") Scanner scan = new Scanner(System.in);
        sop("string should contain only a-z character for all operation");
        while (true) {
            sop("1. Insert\n2. Search\n3. Delete\n4. Quit");
            try {
                int t = scan.nextInt();
                switch (t) {
                case 1:
                    word = scan.next();
                    if (isValid(word)) {
                        obj.insert(word, dani );      // atención que acá metí un parametro string
                    } else {
                        sop("Invalid string: allowed only a-z");
                    }
                    break;
                case 2:
                    word = scan.next();
                    boolean resS = false;
                    if (isValid(word)) {
                        resS = obj.search(word);
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
                        resD = obj.delete(word);
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
