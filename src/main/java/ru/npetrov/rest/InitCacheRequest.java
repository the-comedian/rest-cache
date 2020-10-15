package ru.npetrov.rest;

import ru.npetrov.methods.CacheType;

/**
 * Class for cache initialisation
 */
public class InitCacheRequest {

    private Integer capacity;
    private CacheType type;

    public InitCacheRequest() {
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public CacheType getType() {
        return type;
    }

    public void setType(CacheType type) {
        this.type = type;
    }
}
