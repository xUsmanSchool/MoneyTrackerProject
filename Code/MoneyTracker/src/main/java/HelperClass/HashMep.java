package HelperClass;

public class HashMep<K,V> {
    private K key;
    private V v;

    public HashMep() {}

    public HashMep(K key, V object) {
        this.key = key;
        this.v = object;
    }

    public void put(K key, V object) {
        this.key = key;
        this.v = object;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return v;
    }
}
