import domain.Image
import services.LgtmService
import util.AnalyticsUtil
import util.AppUtil

import java.security.SecureRandom
import java.util.logging.Level

def ct = LgtmService.instance.count
List<Image> imageList = LgtmService.instance.imageList

if (params.username) {
    def myList = LgtmService.instance.getUserList(params.username)
    if (myList.hashes?.size()) {
        imageList = LgtmService.instance.imageList.findAll { img ->
            myList.hashes.contains(img.hash)
        }.sort(false) { img ->
            - img.credits
        }
        ct = imageList.size();
    }
}

if (ct) {
    def random = Math.abs(new SecureRandom().nextGaussian() / 2.0)
    log.info "Random: ${random}"
    def theOffset = Math.floor(random * ct).intValue()
    if (theOffset >= ct) {
        theOffset = theOffset % ct
    }

    log.info "Fetching ${theOffset} of ${ct}"

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
