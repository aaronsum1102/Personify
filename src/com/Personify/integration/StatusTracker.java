package com.Personify.integration;

public class StatusTracker<K, V> {
    private K status;
    private V trackerIndex;

    public StatusTracker(K status, V trackerIndex) {
        this.status = status;
        this.trackerIndex = trackerIndex;
    }

    public void setStatus(K status) {
        this.status = status;
    }

    public void setTrackerIndex(V trackerIndex) {
        this.trackerIndex = trackerIndex;
    }

    public K getStatus() {

        return status;
    }

    public V getTrackerIndex() {
        return trackerIndex;
    }
}
