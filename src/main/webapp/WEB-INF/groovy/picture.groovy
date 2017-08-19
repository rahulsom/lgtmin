import com.google.appengine.api.memcache.Expiration
import domain.Image
import util.AnalyticsUtil
import util.AppUtil

String hash = params.hash
Image image = AppUtil.instance.getCachedValue("/i/${hash}".toString(), Expiration.byDeltaMillis(AppUtil.DAY)) {
    Image.findByHash(hash)
}

log.info "Image: ${image}"

if (image) {
    AnalyticsUtil.sendInfo(request, response, "/p/${image.hash}", "Picture Forward")
    redirect(image.imageUrl)
} else {
    response.sendError(404)
}