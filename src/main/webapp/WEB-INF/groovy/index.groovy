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
        LgtmService.instance.topImages.
                zipWith(userList) { images, ul ->
                    images.collect { new ImageHolder(it, ul.contains(it.hash)) }
                }.
                blockingGet()

log.info "Images: ${imageList}"
request.setAttribute 'imageList', imageList
request.setAttribute 'appUtil', AppUtil.instance
response.setHeader 'Strict-Transport-Security', 'max-age=300; preload'
forward '/WEB-INF/pages/index.gtpl'