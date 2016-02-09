import com.google.appengine.api.datastore.FetchOptions
import com.google.appengine.api.datastore.Query

log.info "Clearing sessions"
def query = new Query('_ah_SESSION')
query.setFilter(new Query.FilterPredicate('_expires', Query.FilterOperator.LESS_THAN, new Date().time))
def results = datastore.prepare(query)

try {
  results.asIterable(FetchOptions.Builder.withLimit(1000)).each {
    datastore.delete(it.key)
  }
} catch (Throwable e) {
  log.error "Could not delete", e
}
log.info "Done clearing sessions"