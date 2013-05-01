import domain.Image

log.info "Setting attributes"
String hash = params.hash
Image image = Image.findByHash(hash)
log.info "Image: ${image}"
if (image) {
  datastore.withTransaction {
    image.dislikes ++
    image.updateCredits()
    image.save()
  }
  redirect("/i/${hash}")
} else {
  redirect('/')
}
