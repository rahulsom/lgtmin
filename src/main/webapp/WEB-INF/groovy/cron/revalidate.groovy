import domain.Image
import domain.UniqueConstraintViolatedException
import domain.ValidationException

log.info("Starting cron.revalidate")
Image.findAll().each { Image i ->
	try {
		i.validate(false)
	} catch (ValidationException e) {
		log.info("Removing ${i}")
		i.delete()
	}
}
memcache.clearAll()
log.info("Done cron.revalidate")
