import domain.Image
import util.AnalyticsUtil
import util.AppUtil

import java.security.SecureRandom
import java.util.logging.Level

import static util.AppUtil.TOP_IMAGES

def ct = AppUtil.instance.getCachedValue(AppUtil.COUNT) {
	Image.count()
}

if (ct) {
  def random = Math.abs(new SecureRandom().nextGaussian()/2.5)
  log.info "Random: ${random}"
  if (random >= 1) {
    random = 0
  }
  def theOffset = Math.floor(random * (ct -1)).intValue()
  if (theOffset > ct) {
    theOffset = 0
  }

  log.info "Fetching ${theOffset} of ${ct}"
  Image image = null
  if (theOffset < 12) {
    def imageList = AppUtil.instance.getCachedValue(TOP_IMAGES) {
      Image.listSortedByCredits(12)
    }
    image = imageList [theOffset]
  } else {
    def imageList = Image.listSortedByCredits(1, theOffset)
    log.info "Images: ${imageList.join('\n')}"
    image = imageList [0]
  }

  try {
    datastore.withTransaction {
      image.impressions ++
      image.updateCredits()
      image.save()
    }
  } catch (Exception e) {
    log.log(Level.WARNING ,"Exception updating impressions on image", e)
  }

  request.setAttribute 'image', image

} else {
  def image = new Image(imageUrl: 'https://f.cloud.github.com/assets/193047/406777/c9e3e3a2-aaba-11e2-8e03-4720321204f7.png')
  request.setAttribute 'image', image
}

response.setHeader("Access-Control-Allow-Origin", "*")
response.setHeader("Access-Control-Allow-Methods", "GET")
response.setHeader("Access-Control-Allow-Credentials", "true")

if (request.getHeader('Accept')?.contains('application/json')) {
  response.setHeader("Content-Type", "application/json")
  Image image = request.getAttribute('image')
  AnalyticsUtil.sendInfo(request, "/i/${image.hash}")
  out.write(image.toJson())
} else {
  response.setHeader("Content-Type", "text/html")
  forward '/WEB-INF/pages/show.html.gtpl'
}
