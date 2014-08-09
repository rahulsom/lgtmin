import domain.Image
import util.AppUtil

def ct = AppUtil.instance.getCachedValue(AppUtil.COUNT) {
    Image.countNotDeleted()
}

def imageList = AppUtil.instance.getCachedValue(AppUtil.ALL_IMAGES) {
    Image.listSortedByCredits(ct)
}

request.setAttribute 'imageList', imageList

forward '/WEB-INF/pages/browse.gtpl'