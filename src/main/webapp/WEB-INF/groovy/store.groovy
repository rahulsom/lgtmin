log.info "Setting attributes"

log.info "Params: ${params}"
String imageUrl = params.imageUrl
log.info "Image Url saved will be ${imageUrl}"

if (imageUrl.startsWith('http://') || imageUrl.startsWith('https://')) {
  Image existingImage = Image.findByUrl(imageUrl)
  if (existingImage) {
    request.setAttribute('message', 'That image was already uploaded.')
    redirect "/i/${existingImage.hash}"
  } else {
    def newImage = new Image()
    newImage.imageUrl = imageUrl

    newImage.save()

    request.setAttribute 'image', newImage
    request.setAttribute 'dataUrl', "http://lgtm.in/i/${newImage.hash}"

    response.setHeader("Content-Type", "text/html");
    redirect "/i/${newImage.hash}"
  }
} else {

  request.setAttribute 'message', "Only http and https urls are valid"
  request.setAttribute 'imageUrl', imageUrl
  response.setHeader "Content-Type", "text/html"
  forward '/WEB-INF/pages/upload.gtpl'

}

