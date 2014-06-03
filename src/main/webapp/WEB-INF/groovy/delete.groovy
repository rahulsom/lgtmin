import com.google.appengine.api.memcache.Expiration
import util.AppUtil

import domain.Image

String hash = params.hash

if (session?.getAttribute('githubUsername') == 'rahulsom') {
    Image image = Image.findByHash(hash)

    log.info "Image: ${image}"

    if (image) {
        image.delete()
        AppUtil.instance.evictCache(AppUtil.TOP_IMAGES)
        AppUtil.instance.evictCache(AppUtil.COUNT)
        AppUtil.instance.evictCache("/i/${hash}")
        redirect('/')
    } else {
        response.sendError(404)
    }
} else {
    response.sendError(403)
}
