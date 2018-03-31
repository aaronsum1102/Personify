package com.Personify.util;

/**
 * Provide a generic implementation of key pairs. It provide methods to set and get the
 * information pair.
 *
 * @param <K> key of a <code>InformationPair</code> object.
 * @param <V> value of a <code>InformationPair</code> object.
 */
public class InformationPair<K, V> {
    private K key;
    private V value;

    /**
     * Construct a <code>InformationPair</code> object with specify key and value.
     *
     * @param key   key of a <code>InformationPair</code> object.
     * @param value value of a <code>InformationPair</code> object.
     */
    public InformationPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Provide the key of a <code>InformationPair</code> object.
     *
     * @return key of a <code>InformationPair</code> object.
     */
    public K getKey() {
        return key;
    }

    /**
     * Set the key of a <code>InformationPair</code> object.
     *
     * @param key new key to set for a <code>InformationPair</code> object.
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Provide the value of a <code>InformationPair</code> object.
     *
     * @return value for the key.
     */
    public V getValue() {
        return value;
    }

    /**
     * Set the value of a <code>InformationPair</code> object.
     *
     * @param value new value to set for a <code>InformationPair</code> object.
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Provide String representation of <code>InformationPair</code> object.
     *
     * @return a String representation of <code>InformationPair</code> object.
     */
    @Override
    public String toString() {
        return String.format("%s;%s", key, value);
    }
}
