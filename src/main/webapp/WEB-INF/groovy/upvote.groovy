import util.AppUtil
import domain.Image

log.info "Setting attributes"
String hash = params.hash
Image.findByHash(hash)
log.info "Image: ${image}"
if (image) {
  datastore.withTransaction {
    image.likes ++
    image.updateCredits()
    image.save()
  }
  redirect("/i/${hash}")
} else {
  redirect('/')
}
