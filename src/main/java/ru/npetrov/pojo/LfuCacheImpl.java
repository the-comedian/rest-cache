package ru.npetrov.pojo;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of LFU (Least frequently used) cache
 *
 * @param <K> - key type
 * @param <V> - value type
 */
public class LfuCacheImpl<K, V> extends CacheImpl<K, V> {

    public static final String TYPE = "LFU";

    private static final Logger logger = LoggerFactory.getLogger(LfuCacheImpl.class.getName());

    /**
     * Map to hold request frequency
     */
    private Map<K, Integer> frequencyMap;

    public LfuCacheImpl(Integer capacity) {
        super(capacity);
        this.cache = new HashMap<>();
        this.frequencyMap = new HashMap<>();
    }

    @Override
    public V get(K key) {
        // before value return we need to increase frequency
        Integer valueFrequency = frequencyMap.get(key);
        if (valueFrequency == null) {
            valueFrequency = 0;
        }
        valueFrequency++;
        frequencyMap.put(key, valueFrequency);
        logger.info("getting element with key: " + key);
        logger.info("key frequency: " + valueFrequency);
        return cache.get(key);
    }

    @Override
    public void resolve(K key, V value) {
        logger.info("resolving element with key: " + key);
        // value access frequency was increased is get method
        Integer keyFrequency = frequencyMap.get(key);
        logger.info("key frequency: " + keyFrequency);
        // we only interested in case when value is not in cache
        if (cache.get(key) == null) {
            // if cache is not full, we can easily fill it with other value
            if (cache.size() < capacity) {
                logger.info("capacity is not full, putting " + key + ":" + value);
                cache.put(key, value);
            } else {
                // else we need to find the least used element
                K keyToSwap = null;
                logger.info("capacity is full finding element to swap");
                for (Map.Entry<K, Integer> entry : frequencyMap.entrySet()) {
                    if (entry.getValue() <= keyFrequency) {
                        keyToSwap = entry.getKey();
                    }
                }
                // if the least used element was found, swap it with current element
                if (keyToSwap != null) {
                    logger.info("key to swap" + keyToSwap);
                    cache.remove(keyToSwap);
                    logger.info("putting " + key + ":" + value);
                    cache.put(key, value);
                }
            }
        }
    }

    /**
     * Method that return cache information (composition e.g.)
     */
    @Override
    public Map getCacheInfo() {
        Map<String, Object> result = new HashMap<>();
        result.put("cache", cache);
        result.put("frequencyMap", frequencyMap);
        result.put("type", TYPE);
        return result;
    }
}
