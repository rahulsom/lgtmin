import com.google.appengine.api.memcache.Expiration
import util.AppUtil

import domain.Image

String hash = params.hash
Image image = AppUtil.instance.getCachedValue("/i/${hash}", Expiration.byDeltaMillis(AppUtil.DAY)) {
    Image.findByHash(hash)
}

log.info "Image: ${image}"

if (image) {
    request.setAttribute 'image', image
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "GET");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    if (request.getHeader('Accept')?.contains('application/json')) {
        response.setHeader("Content-Type", "application/json");
        out.write(image.toJson())
    } else {
        response.setHeader("Content-Type", "text/html");
        request.setAttribute('comments', true)
        forward '/WEB-INF/pages/show.html.gtpl'
    }
} else {
    redirect('/')
}
