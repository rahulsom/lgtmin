package util

import com.google.appengine.api.memcache.AsyncMemcacheService
import com.google.appengine.api.memcache.ErrorHandlers
import com.google.appengine.api.memcache.Expiration
import com.google.appengine.api.memcache.MemcacheServiceFactory
import groovy.util.logging.Log
import groovyx.gaelyk.GaelykBindings

import java.util.concurrent.Future
import java.util.logging.Level

/**
 * Helps extract the application's root url
 * @author rahulsomasunderam
 */
@GaelykBindings
@Singleton
@Log
class AppUtil {

    public static final int SECOND = 1000
    public static final int MINUTE = 60 * SECOND
    public static final int HOUR = 60 * MINUTE
    public static final int DAY = 24 * HOUR

    /**
     * Application root
     * @return
     */
    String getRoot() {
        app.env.name.toString() == 'Development' ? 'http://localhost:8080' : 'http://www.lgtm.in'
    }

    public static final String TOP_IMAGES = "TopImages"
    public static final String ALL_IMAGES = "AllImages"
    public static final String COUNT = "Count"

    /**
     * Returns a value from cache if possible, evaluates otherwise
     * @param cacheName The name for the cached value
     * @param expiration default cache expiration if evaluating. 1 Hour by default
     * @param closure The eval for cached value
     * @return The value
     */
    public <T> T getCachedValue(
            String cacheName,
            Expiration expiration = Expiration.byDeltaMillis(HOUR),
            Closure<T> closure
    ) {
        AsyncMemcacheService asyncCache = MemcacheServiceFactory.getAsyncMemcacheService()
        asyncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO))
        Future<Object> futureValue = asyncCache.get(cacheName); // read from cache

        T value
        try {
            value = futureValue.get()
        } catch (Exception e) {
            value = null
        }
        if (value == null) {
            log.fine("Missed cache for ${cacheName}")
            value = closure.call()
            asyncCache.put(cacheName, value, expiration)
        }

        value
    }

    /**
     * Evicts value from cache
     * @param cacheName name of cached value
     */
    void evictCache(String cacheName) {
        AsyncMemcacheService asyncCache = MemcacheServiceFactory.getAsyncMemcacheService()
        asyncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO))

        asyncCache.delete(cacheName)
    }

}
