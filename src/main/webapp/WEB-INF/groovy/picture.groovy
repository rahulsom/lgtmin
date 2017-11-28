import domain.Image
import util.AppUtil

import static com.google.appengine.api.memcache.Expiration.byDeltaMillis
import static util.AnalyticsUtil.sendInfo
import static util.AppUtil.DAY

String hash = params.hash
def image = AppUtil.instance.
        getCachedValue("/i/${hash}".toString(), byDeltaMillis(DAY)) { Image.findByHash(hash) }.
        blockingGet()

log.info "Image: ${image}"

if (image) {
    sendInfo(request, response, "/p/${image.hash}", "Picture Forward")
    redirect(image.imageUrl)
} else {
    response.sendError(404)
}