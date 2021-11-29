package HelperClass;

public class HashMep<String,V> {
    private String key;
    private V v;

    public HashMep() {}

    public HashMep(String key, V object) {
        this.key = key;
        this.v = object;
    }

    public void put(String key, V object) {
        this.key = key;
        this.v = object;
    }

    public String getKey() {
        return this.key;
    }

    public V getValue() {
        return v;
    }
}
