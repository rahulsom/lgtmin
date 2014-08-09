import domain.Image
import domain.UserList
import services.LgtmService
import util.AppUtil
import util.GithubAuthUtil

def githubAuthUtil = new GithubAuthUtil(request, response)

String hash = params.hash
if (hash) {
    def image = Image.findByHash(hash)
    log.info "Image: ${image}"
    if (image) {
        githubAuthUtil.withValidUser("/u/${hash}") {
            def userName = session.getAttribute('githubUsername')
            log.info "Username: ${userName}"
            UserList myList = LgtmService.instance.getUserList(userName)

            if (myList.hashes.contains(hash)) {
                myList.hashes.remove(hash)
            } else {
                myList.hashes.add(hash)
            }

            AppUtil.instance.evictCache("/l/${userName}")
            myList.save()

        }
        AppUtil.instance.evictCache("/i/${hash}")
        redirect("/i/${hash}")
    } else {
        redirect('/')
    }
}
