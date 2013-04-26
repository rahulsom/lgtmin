log.info "Setting attributes"

log.info "Params: ${params}"
String imageUrl = params.imageUrl
log.info "Image Url saved will be ${imageUrl}"

if (imageUrl.startsWith('http://') || imageUrl.startsWith('https://')) {
  def e = new Image()
  e.imageUrl = imageUrl

  e.save()

  request.setAttribute 'image', e
  request.setAttribute 'dataUrl', "http://lgtm.in/i/${e.hash}"

  response.setHeader("Content-Type", "text/html");
  redirect "/i/${e.hash}"
} else {

  request.setAttribute 'message', "Only http and https urls are valid"
  request.setAttribute 'imageUrl', imageUrl
  response.setHeader "Content-Type", "text/html"
  forward '/WEB-INF/pages/upload.gtpl'

}

