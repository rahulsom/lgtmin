import domain.Image
import domain.UserList
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
            def myList = UserList.find {
                where username == userName
            }
            if (!myList) {
                log.info "Creating list for user ${userName}"
                myList = new UserList(username: userName, hashes: [])
                myList.save()
            }

            /*datastore.withTransaction {
                image.likes++
                image.updateCredits()
                image.save()
            } */
        }
        AppUtil.instance.evictCache("/i/${hash}")
        redirect("/i/${hash}")
    } else {
        redirect('/')
    }
}
