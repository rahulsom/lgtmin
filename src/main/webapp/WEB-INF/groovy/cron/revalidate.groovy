import domain.Image
import domain.ValidationException

log.info("Starting cron.revalidate")
Image.findAll().each { Image i ->
	try {
		i.validate()
	} catch (ValidationException e) {
		log.fine("Removing ${i}")
		i.delete()
	}
}
memcache.clearAll()
log.info("Done cron.revalidate")
