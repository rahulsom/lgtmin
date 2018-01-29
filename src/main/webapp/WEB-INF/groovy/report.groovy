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
        githubAuthUtil.withValidUser("/r/${hash}") {
            if (addToUsersDislikes(githubAuthUtil.username, image)) {
                updateImageDislikes(image)
            }
            AppUtil.instance.evictCache("/i/${hash}").toFlowable().blockingFirst()
            redirect("/i/${hash}")
        }
    } else {
        redirect('/')
    }
}

private void updateImageDislikes(Image image) {
    datastore.withTransaction {
        image.dislikes++
        image.updateCredits()
        image.save()
    }
}

private boolean addToUsersDislikes(String username, Image image) {
    boolean added = false
    datastore.withTransaction {
        def user = UserList.findByUsername(username)
        log.info "User: $user"
        if (user.dislikes == null) {
            user.dislikes = []
        }
        if (!user.dislikes.contains(image.hash)) {
            user.dislikes.add(image.hash)
            user.save()
            added = true
        }
    }
    added
}
