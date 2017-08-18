import domain.Image
import services.LgtmService
import util.AnalyticsUtil
import util.AppUtil

import java.security.SecureRandom
import java.util.logging.Level

def ct = LgtmService.instance.count
List<Image> imageList = LgtmService.instance.imageList
Image image = null

if (params.username) {
    log.info "Random for ${params.username}"
    def myList = LgtmService.instance.getUserList(params.username)
    log.info "Mylist computed ${params.username}"
    if (myList.hashes?.size()) {
        def hash = LgtmService.instance.getRandom(myList.hashes, myList.hashes.size())
        image = LgtmService.instance.getImage(hash)
        log.info "Image picked"
    }
} else {
    image = LgtmService.instance.getRandom(imageList, ct)
}

if (image) {
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

    response.sendRedirect(AppUtil.instance.patchUrl(image.dataUrl, request))

} else {
    image = new Image(imageUrl: 'https://f.cloud.github.com/assets/193047/406777/c9e3e3a2-aaba-11e2-8e03-4720321204f7.png')
    request.setAttribute 'image', image

    response.setHeader("Access-Control-Allow-Origin", "*")
    response.setHeader("Access-Control-Allow-Methods", "GET")
    response.setHeader("Access-Control-Allow-Credentials", "true")

    if (request.getHeader('Accept')?.contains('application/json')) {
        response.setHeader("Content-Type", "application/json")
        AnalyticsUtil.sendInfo(request, response, "/i/${image.hash}", 'Random Fetch')
        out.write(image.toJson())
    } else {
        response.setHeader("Content-Type", "text/html")
        forward '/WEB-INF/pages/show.html.gtpl'
    }
}

