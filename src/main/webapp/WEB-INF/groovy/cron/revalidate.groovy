import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.PreparedQuery
import com.google.appengine.api.datastore.Query
import domain.Image
import domain.ValidationException

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit

log.info("Starting cron.revalidate")
def q = new Query('Image').addFilter('isDeleted', Query.FilterOperator.EQUAL, false)
PreparedQuery preparedQuery = datastore.prepare(q)

preparedQuery.asList(withLimit(100)).
        each { Entity entity ->
            Image i = entity as Image
            try {
                i.validate(false)
            } catch (ValidationException e) {
                log.info("Removing ${i}")
                i.isDeleted = true
                i.save()
            } catch (Exception e) {
                log.warning "Unexpected exception: $e for $i"
            }
        }
memcache.clearAll()
log.info("Done cron.revalidate")
