import domain.Image
import util.AppUtil
import util.GithubAuthUtil

def githubAuthUtil = new GithubAuthUtil(request, response)

String hash = params.hash
if (hash) {
    def image = Image.findByHash(hash)
    log.info "Image: ${image}"
    if (image) {
        githubAuthUtil.withValidUser("/r/${hash}") {
            datastore.withTransaction {
                image.dislikes++
                image.updateCredits()
                image.save()
            }
            AppUtil.instance.evictCache("/i/${hash}")
            redirect("/i/${hash}")
        }
    } else {
        redirect('/')
    }
}
