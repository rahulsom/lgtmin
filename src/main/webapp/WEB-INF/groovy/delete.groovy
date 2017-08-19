import domain.Image
import services.LgtmService
import util.AppUtil
import util.AuthorizedUsers

String hash = params.hash

if (session?.getAttribute('githubUsername') in AuthorizedUsers.allowDelete) {
    Image image = Image.findByHash(hash)

    log.info "Image: ${image}"

    if (image) {

        if (params.ban == "true" && image.uploader) {
            def ul = LgtmService.instance.getUserList(image.uploader)
            ul.bannedFromUpload = Boolean.TRUE
            ul.save()
        }

        image.isDeleted = true
        image.save()
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
