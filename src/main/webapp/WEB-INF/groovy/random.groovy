import domain.Image

import java.security.SecureRandom

log.info "Setting attributes"
def ct = Image.count()
if (ct) {
  def random = Math.abs(new SecureRandom().nextDouble())
  log.info "Random: ${random}"
  def theOffset = Math.floor(random * (ct -1)).intValue()
  datastore.withTransaction {

    log.info "Fetching ${theOffset} of ${ct}"
    def imageList = Image.findAll {
      sort 'desc' by 'credits'
      limit 1
      offset theOffset
    }

    log.info "Images: ${imageList}"

    Image image = imageList [0]
    image.impressions ++
    image.updateCredits()
    image.save()

    request.setAttribute 'image', image
  }

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
