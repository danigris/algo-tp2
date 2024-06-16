package aed;

import java.util.HashMap;

public class DiccionarioHashMap<K, V> implements Diccionario<K, V> {

    private final HashMap<K, V> map = new HashMap<>();

    @Override
    public boolean diccionarioVacio() {
        return map.isEmpty();
    }

    @Override
    public boolean esta(K key) {
        return map.containsKey(key);
    }

    @Override
    public void definir(K key, V value) {
        map.put(key, value);
    }

    @Override
    public V obtener(K key) {
        return map.get(key);
    }

    @Override
    public void borrar(K key) {
        map.remove(key);
    }

    @Override
    public int tamaño() {
        return map.size();
    }

    @Override
    public String toString() {
        return map.toString();
    }

}
