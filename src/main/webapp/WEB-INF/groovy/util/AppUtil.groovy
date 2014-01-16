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
  String getRoot() {
    app.env.name.toString() == 'Development' ? 'http://localhost:8080' : 'http://www.lgtm.in'
  }

	public static final String TOP_IMAGES = "TopImages"
	public static final String COUNT = "Count"

	def <T> T getCachedValue(String cacheName, Expiration expiration = new Expiration(5*60*1000, true), Closure<T> closure) {
		AsyncMemcacheService asyncCache = MemcacheServiceFactory.getAsyncMemcacheService();
		asyncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		Future<Object> futureValue = asyncCache.get(cacheName); // read from cache

		T value = (byte[]) futureValue.get();
		if (value == null) {
			log.fine("Missed cache for ${cacheName}")
			value = closure.call()
			asyncCache.put(cacheName, value, expiration);
		}

		value
	}

	void evictCache(String cacheName) {
		AsyncMemcacheService asyncCache = MemcacheServiceFactory.getAsyncMemcacheService();
		asyncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));

		asyncCache.delete(cacheName)
	}
}
