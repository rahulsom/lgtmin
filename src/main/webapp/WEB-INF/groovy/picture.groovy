import com.google.appengine.api.memcache.Expiration
import util.AppUtil

import domain.Image

String hash = params.hash
Image image = AppUtil.instance.getCachedValue("/i/${hash}", Expiration.byDeltaMillis(AppUtil.DAY)) {
    Image.findByHash(hash)
}

log.info "Image: ${image}"

if (image) {
    redirect(image.imageUrl)
} else {
    response.sendError(404)
}