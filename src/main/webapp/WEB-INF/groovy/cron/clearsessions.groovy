import com.google.appengine.api.datastore.FetchOptions
import com.google.appengine.api.datastore.Query

import static com.google.appengine.api.datastore.Query.SortDirection.ASCENDING

log.info "Clearing sessions"
def query = new Query('_ah_SESSION').
        setFilter(new Query.FilterPredicate('_expires', Query.FilterOperator.LESS_THAN, new Date().time)).
        setKeysOnly().
        addSort('_expires', ASCENDING)

def preparedQuery = datastore.prepare(query)

int count = 0
try {
    preparedQuery.
            asIterable(FetchOptions.Builder.withLimit(1000)).
            each {
                datastore.delete(it.key)
                count++
            }
} catch (Throwable e) {
    log.error "Could not delete", e
}
log.info "Done clearing $count sessions"