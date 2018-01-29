import domain.Image
import io.reactivex.Flowable
import services.LgtmService
import util.AppUtil
import util.AuthorizedUsers

String hash = params.hash

if (session?.getAttribute('githubUsername') in AuthorizedUsers.allowDelete) {
    Image image = Image.findByHash(hash)

    log.info "Image: ${image}"

    if (image) {

        if (params.ban == "true" && image.uploader) {
            def ul = LgtmService.instance.getUserList(image.uploader).blockingGet()
            ul.bannedFromUpload = Boolean.TRUE
            ul.save()
        }

        image.isDeleted = true
        image.save()
        Flowable.combineLatest(
                AppUtil.instance.evictCache(AppUtil.TOP_IMAGES).toFlowable(),
                AppUtil.instance.evictCache(AppUtil.COUNT).toFlowable(),
                AppUtil.instance.evictCache("/i/${hash}").toFlowable()
        ) { a, b, c -> a && b && c }.blockingFirst()
        redirect('/')
    } else {
        response.sendError(404)
    }
} else {
    response.sendError(403)
}
