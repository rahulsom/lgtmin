import domain.ImageHolder
import io.reactivex.Maybe
import services.LgtmService
import util.AppUtil
import util.GithubAuthUtil

def authUtil = new GithubAuthUtil(request, response)
def userList =
        authUtil.isAuthenticated() ?
                LgtmService.instance.getUserList(authUtil.username).
                        map { it.hashes }.
                        switchIfEmpty(Maybe.just([] as List<String>)) :
                Maybe.just([] as List<String>)

def imageList =
        LgtmService.instance.imageList.
                zipWith(userList) { images, ul ->
                    images.collect { new ImageHolder(it, ul.contains(it.hash)) }
                }.
                blockingGet()

def PAGESIZE = 32
def page = 1
if (params.page && params.page.toString().isInteger()) {
    page = params.page as int
}
def start = (page - 1) * PAGESIZE
def stop = Math.min(start + PAGESIZE - 1, imageList.size() - 1)

if (start <= stop) {
    request.setAttribute 'imageList', imageList[start..stop]
    request.setAttribute 'appUtil', AppUtil.instance
    if (stop + 1 < imageList.size()) {
        request.setAttribute 'next', page + 1
    }
    if (start > 0) {
        request.setAttribute 'prev', page - 1
    }
    if (params.page != null) {
        forward '/WEB-INF/pages/browse.gtpl'
    } else {
        forward '/WEB-INF/pages/browsePage.gtpl'
    }
} else {
    response.sendError(404)
}
