import util.AppUtil

import domain.Image

log.info "Setting attributes"
String hash = params.hash
Image image = AppUtil.instance.getCachedValue("/i/${hash}") {
  Image.findByHash(hash)
}

log.info "Image: ${image}"
log.info "App: ${app.env.name}"

if (image) {
  request.setAttribute 'image', image
  response.setHeader("Access-Control-Allow-Origin", "*");
  response.setHeader("Access-Control-Allow-Methods", "GET");
  response.setHeader("Access-Control-Allow-Credentials", "true");
  if (request.getHeader('Accept').contains('application/json')) {
    response.setHeader("Content-Type", "application/json");
    out.write(image.toJson())
  } else {
    response.setHeader("Content-Type", "text/html");
    request.setAttribute('comments', true)
    log.info "Forwarding to the template"
    forward '/WEB-INF/pages/show.html.gtpl'
  }
} else {
  redirect('/')
}
