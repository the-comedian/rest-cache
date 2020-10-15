package ru.npetrov.pojo;

import java.util.Map;

/**
 * Base class for caches
 *
 * @param <K> - key type
 * @param <V> - value type
 */
public class CacheImpl<K, V> implements ICache<K, V> {

    /**
     * Needed cache object
     */
    protected Map<K, V> cache;

    /**
     * Capacity of cache item
     */
    protected Integer capacity;

    public CacheImpl(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * Method for getting value from cache by key
     *
     * @param key - key
     */
    @Override
    public V get(K key) {
        return null;
    }

    /**
     * Method for resolving cache value (to set or not to set)
     *
     * @param key   - key
     * @param value - value
     */
    @Override
    public void resolve(K key, V value) {

    }

    /**
     * Method that return cache information (composition e.g.)
     */
    public Map getCacheInfo() {
        return null;
    }

}
