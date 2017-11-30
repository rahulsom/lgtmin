import domain.Image
import domain.ImageHolder
import io.reactivex.Maybe
import services.LgtmService
import util.AppUtil
import util.GithubAuthUtil

def imageList =
        LgtmService.instance.topImages.
                flatMap { List<Image> topImages ->
                    def authUtil = new GithubAuthUtil(request, response)
                    if (authUtil.isAuthenticated()) {
                        LgtmService.instance.getUserList(authUtil.username).
                                flatMap { ul ->
                                    Maybe.just(topImages.collect {new ImageHolder(it, ul.hashes.contains(it.hash))})
                                }
                    } else {
                        Maybe.just(topImages.collect {new ImageHolder(it, false)})
                    }
                }.
                blockingGet()

log.info "Images: ${imageList}"
request.setAttribute 'imageList', imageList
request.setAttribute 'appUtil', AppUtil.instance
forward '/WEB-INF/pages/index.gtpl'