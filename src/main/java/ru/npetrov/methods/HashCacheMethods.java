package ru.npetrov.methods;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import ru.npetrov.pojo.ICache;
import ru.npetrov.pojo.LfuCacheImpl;
import ru.npetrov.pojo.LruCacheImpl;
import ru.npetrov.rest.InitCacheRequest;

import javax.enterprise.context.ApplicationScoped;
import java.security.MessageDigest;
import java.util.Map;

/**
 * Class for holding data methods with cache usage
 */
@ApplicationScoped
public class HashCacheMethods {

    private static final Logger logger = LoggerFactory.getLogger(HashCacheMethods.class.getName());

    private static final Integer DEFAULT_CAPACITY = 10;

    private ICache<String, String> cache;

    {
        logger.info("calling init method");
        cache = new LfuCacheImpl<>(DEFAULT_CAPACITY);
        logger.info("init method end");
    }

    /**
     * Method for cache initialisation
     */
    public void initCache(InitCacheRequest request) {
        logger.info("calling initCache with request: " + request);
        CacheType cacheType = request.getType();
        switch (cacheType) {
            case LFU: {
                cache = new LfuCacheImpl<>(request.getCapacity());
                break;
            }
            case LRU: {
                cache = new LruCacheImpl<>(request.getCapacity());
                break;
            }
        }
        logger.info("call initCache end");
    }

    /**
     * Get result of hashing
     */
    public String getResult(String data) throws Exception {
        logger.info("calling getResult with data: " + data);
        String result = null;
        try {
            result = cache.get(data);
            if (result == null) {
                result = applySha256(data);
                cache.resolve(data, result);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
        logger.info("call getResult end");
        return result;
    }

    /**
     * Applies Sha256 to a string and returns the result.
     */
    private String applySha256(String input) throws Exception {
        logger.info("calling applySha256 with input: " + input);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Applies sha256 to our input,
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            logger.info("call applySha256 end");
            return hexString.toString();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Cache info
     *
     * @return map with cache info
     */
    public Map getCacheInfo() {
        return cache.getCacheInfo();
    }

}
