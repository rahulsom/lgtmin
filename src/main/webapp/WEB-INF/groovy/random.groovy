import domain.Image
import io.reactivex.Maybe
import services.LgtmService
import util.AnalyticsUtil
import util.AppUtil

import java.util.logging.Level


def defaultImage = new Image(imageUrl: 'https://f.cloud.github.com/assets/193047/406777/c9e3e3a2-aaba-11e2-8e03-4720321204f7.png')
def lgtmService = LgtmService.instance

Image image = lgtmService.count.
        flatMap { int ct ->
            lgtmService.imageList.flatMap { List<Image> imageList ->
                if (params.username) {
                    log.info "Random for ${params.username}"
                    lgtmService.getUserList(params.username).flatMap { myList ->
                        log.info "Mylist computed ${params.username}"
                        if (myList.hashes?.size()) {
                            def hash = lgtmService.getRandom(myList.hashes, myList.hashes.size())
                            def image = lgtmService.getImage(hash)
                            log.info "Image picked"
                            image
                        } else {
                            Maybe.just(defaultImage)
                        }
                    }
                } else {
                    Maybe.just lgtmService.getRandom(imageList, ct)
                }

            }
        }.
        blockingGet()


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
    image = defaultImage
    request.setAttribute 'image', image
    request.setAttribute 'appUtil', AppUtil.instance

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

