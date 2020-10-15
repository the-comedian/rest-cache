package ru.npetrov.pojo;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of LRU (Least recently used) cache
 *
 * @param <K> - key type
 * @param <V> - value type
 */
public class LruCacheImpl<K, V> extends CacheImpl<K, V> {

    public static final String TYPE = "LRU";

    private static final Logger logger = LoggerFactory.getLogger(LruCacheImpl.class.getName());
    /**
     * List to hold recently requested keys
     */
    private List<K> fifo;

    public LruCacheImpl(Integer capacity) {
        super(capacity);
        this.cache = new HashMap<>();
        this.fifo = new ArrayList<>();
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void resolve(K key, V value) {
        logger.info("resolving element with key: " + key);
        // we only interested in case when value is not in cache
        if (cache.get(key) == null) {
            // adding key to the first place of order
            fifo.add(0, key);
            logger.info("added " + key);
            int fifoSize = fifo.size();
            cache.put(key, value);
            // if fifo size is bigger than capacity
            if (fifoSize > capacity) {
                // removing the last element
                K keyToRemove = this.fifo.remove(fifoSize - 1);
                cache.remove(keyToRemove);
                logger.info("removed " + keyToRemove);
            }
        }
    }

    @Override
    public Map getCacheInfo() {
        Map<String, Object> result = new HashMap<>();
        result.put("cache", cache);
        result.put("type", TYPE);
        result.put("fifo", fifo);
        return result;
    }
}
