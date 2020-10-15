package ru.npetrov.pojo;

import java.util.Map;

/**
 * Interface for requires caches
 */
public interface ICache<K, V> {

    /**
     * Method for getting value from cache by key
     *
     * @param key - key
     * @return value
     */
    public V get(K key);

    /**
     * Method for resolving cache value (to set or not to set)
     *
     * @param key   - key
     * @param value - value
     */
    public void resolve(K key, V value);

    /**
     * @return cache information (composition e.g.)
     */
    public Map getCacheInfo();
}
