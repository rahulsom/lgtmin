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
            if (addToUsersLikes(githubAuthUtil.username, image)) {
                updateImageLikes(image)
            }
        }
        AppUtil.instance.evictCache("/i/${hash}").toFlowable().blockingFirst()
        redirect("/i/${hash}")
    } else {
        redirect('/')
    }
}

private void updateImageLikes(Image image) {
    datastore.withTransaction {
        image.likes++
        image.updateCredits()
        image.save()
    }
}

private boolean addToUsersLikes(String username, Image image) {
    boolean added = false
    datastore.withTransaction {
        def user = UserList.findByUsername(username)
        log.info "User: $user"
        if (user.likes == null) {
            user.likes = []
        }
        if (!user.likes.contains(image.hash)) {
            user.likes.add(image.hash)
            user.save()
            added = true
        }
    }
    added
}
