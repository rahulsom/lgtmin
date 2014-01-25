import domain.Image
import domain.ValidationException

if (request.getHeader('X-AppEngine-Cron') == 'true') {
	Image.findAll().each { Image i ->
		try {
			i.validate()
		} catch (ValidationException e) {
			log.fine("Removing ${i}")
			i.delete()
		}
	}
	memcache.clearAll()
} else {
	redirect('http://foaas.com/you/@rahulsom')
}
