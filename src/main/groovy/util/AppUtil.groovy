package util

import com.google.appengine.api.memcache.AsyncMemcacheService
import com.google.appengine.api.memcache.Expiration
import com.google.appengine.api.memcache.MemcacheServiceFactory
import groovy.util.logging.Log
import groovyx.gaelyk.GaelykBindings
import io.reactivex.Maybe

import javax.servlet.http.HttpServletRequest
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.function.Supplier

import static com.google.appengine.api.memcache.ErrorHandlers.getConsistentLogAndContinue
import static com.google.appengine.api.memcache.Expiration.byDeltaMillis
import static java.util.logging.Level.INFO

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
        app.env.name.toString() == 'Development' ? 'http://localhost:8080' : 'https://lgtm.in'
    }

    public static final String TOP_IMAGES = "TopImages"
    public static final String ALL_IMAGES = "AllImages"
    public static final String COUNT = "Count"

    static class SupplierCallableBridge<T> implements Callable<T> {

        Supplier<T> supplier

        SupplierCallableBridge(Supplier<T> supplier) {
            this.supplier = supplier
        }

        @Override
        T call() throws Exception {
            supplier.get()
        }
    }

    /**
     * Returns a value from cache if possible, evaluates otherwise
     * @param cacheName The name for the cached value
     * @param expiration default cache expiration if evaluating. 1 Hour by default
     * @param closure The eval for cached value
     * @return The value
     */
    static <T> Maybe<T> getCachedValue(
            String cacheName, Expiration expiration = byDeltaMillis(HOUR), Supplier<T> closure) {
        def theCache = MemcacheServiceFactory.asyncMemcacheService
        theCache.setErrorHandler getConsistentLogAndContinue(INFO)

        def valueFromCache = Maybe.fromFuture((Future<T>) theCache.get(cacheName)).
                doOnSuccess { log.info "Retrieved value from cache: ${it.class}" }.
                doOnError { log.warning "Caught $e trying to get value from cache" }

        def valueFromClosure = Maybe.
                fromCallable(new SupplierCallableBridge(closure)).
                doOnSubscribe { log.info "Missed cache for ${cacheName}" }.
                doOnSuccess { theCache.put(cacheName, it, expiration) }

        return valueFromCache.
                onErrorResumeNext(valueFromClosure).
                switchIfEmpty(valueFromClosure)
    }

    static <T> void store(String cacheName, T t, Expiration expiration = byDeltaMillis(HOUR)) {
        def theCache = MemcacheServiceFactory.asyncMemcacheService
        theCache.setErrorHandler getConsistentLogAndContinue(INFO)

        theCache.put cacheName, t, expiration
    }

    /**
     * Evicts value from cache
     * @param cacheName name of cached value
     */
    static void evictCache(String cacheName) {
        def cache = MemcacheServiceFactory.memcacheService
        cache.errorHandler = getConsistentLogAndContinue(INFO)
        cache.delete cacheName
    }

    String patchUrl(String input, HttpServletRequest request) {
        def newContext = request.requestURL - request.requestURI
        input.replace root, newContext
    }

}
