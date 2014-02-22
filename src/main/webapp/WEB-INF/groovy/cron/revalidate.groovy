import domain.Image
import domain.ValidationException

log.info("Starting cron.revalidate")
Image.findAll().each { Image i ->
    try {
        i.validate(false)
    } catch (ValidationException e) {
        log.info("Removing ${i}")
        i.delete()
    } catch (Exception e) {
        log.warning "Unexpected exception: $e for $i"
    }
}
memcache.clearAll()
log.info("Done cron.revalidate")
