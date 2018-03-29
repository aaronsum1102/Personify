package com.Personify.util;

/**
 * Provide a generic implementation of primaryInformation pairs. It provide methods to set and get the
 * information pair.
 *
 * @param <K> primaryInformation of a <code>InformationPair</code> object.
 * @param <V> secondaryInformation of a <code>InformationPair</code> object.
 */
public class InformationPair<K, V> {
    private K primaryInformation;
    private V secondaryInformation;

    /**
     * Construct a <code>InformationPair</code> object with specify primaryInformation and secondaryInformation.
     *
     * @param primaryInformation   primaryInformation of a <code>InformationPair</code> object.
     * @param secondaryInformation secondaryInformation of a <code>InformationPair</code> object.
     */
    public InformationPair(K primaryInformation, V secondaryInformation) {
        this.primaryInformation = primaryInformation;
        this.secondaryInformation = secondaryInformation;
    }

    /**
     * Provide the primaryInformation of a <code>InformationPair</code> object.
     *
     * @return primaryInformation of a <code>InformationPair</code> object.
     */
    public K getPrimaryInformation() {
        return primaryInformation;
    }

    /**
     * Set the primaryInformation of a <code>InformationPair</code> object.
     *
     * @param primaryInformation new primaryInformation to set for a <code>InformationPair</code> object.
     */
    public void setPrimaryInformation(K primaryInformation) {
        this.primaryInformation = primaryInformation;
    }

    /**
     * Provide the secondaryInformation of a <code>InformationPair</code> object.
     *
     * @return secondaryInformation for the primaryInformation.
     */
    public V getSecondaryInformation() {
        return secondaryInformation;
    }

    /**
     * Set the secondaryInformation of a <code>InformationPair</code> object.
     *
     * @param secondaryInformation new secondaryInformation to set for a <code>InformationPair</code> object.
     */
    public void setSecondaryInformation(V secondaryInformation) {
        this.secondaryInformation = secondaryInformation;
    }
}
