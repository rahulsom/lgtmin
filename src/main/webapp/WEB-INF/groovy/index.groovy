import util.AppUtil

import domain.Image
import static util.AppUtil.TOP_IMAGES

def imageList = AppUtil.instance.getCachedValue(TOP_IMAGES) {
	Image.listSortedByCredits(12)
}.collect { img ->
	AppUtil.instance.getCachedValue("/i/${img.hash}") {
		Image.findByHash(img.hash)
	}
}

log.info "Images: ${imageList}"
request.setAttribute 'imageList', imageList
forward '/WEB-INF/pages/index.gtpl'