import domain.Image
import util.AnalyticsUtil
import util.AppUtil

import java.security.SecureRandom
import java.util.logging.Level

def ct = AppUtil.instance.getCachedValue(AppUtil.COUNT) {
    Image.count()
}

if (ct) {
    def random = Math.abs(new SecureRandom().nextGaussian() / 2.5)
    log.info "Random: ${random}"
    def theOffset = Math.floor(random * (ct - 1)).intValue()
    if (theOffset > ct - 1) {
        theOffset = 0
    }

    log.info "Fetching ${theOffset} of ${ct}"
    def imageList = AppUtil.instance.getCachedValue(AppUtil.ALL_IMAGES) {
        Image.listSortedByCredits(ct)
    }

    Image image = null
    image = imageList[theOffset]

    try {
        datastore.withTransaction {
            def image1 = Image.findByHash(image.hash)
            image1.impressions++
            image1.updateCredits()
            image1.save()
        }
    } catch (Exception e) {
        log.log(Level.WARNING, "Exception updating impressions on image", e)
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
