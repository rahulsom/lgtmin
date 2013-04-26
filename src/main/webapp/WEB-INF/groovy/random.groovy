import java.security.SecureRandom

log.info "Setting attributes"
def ct = Image.count()
if (ct) {
  def r = new SecureRandom().nextInt(ct)
  def images = Image.findAll {
    limit 1
    offset r
  }

  log.info "Images: ${images}"
  request.setAttribute 'image', images[0]
} else {
  def image = new Image(imageUrl: 'https://f.cloud.github.com/assets/193047/406777/c9e3e3a2-aaba-11e2-8e03-4720321204f7.png')
  request.setAttribute 'image', image
}

response.setHeader("Access-Control-Allow-Origin", "*")
response.setHeader("Access-Control-Allow-Methods", "GET")
response.setHeader("Access-Control-Allow-Credentials", "true")

if (request.getHeader('Accept').contains('application/json')) {
  response.setHeader("Content-Type", "application/json")
  Image image = request.getAttribute('image')
  out.write(image.toJson())
} else {
  response.setHeader("Content-Type", "text/html")
  log.info "Forwarding to the template"
  forward '/WEB-INF/pages/show.html.gtpl'
}
