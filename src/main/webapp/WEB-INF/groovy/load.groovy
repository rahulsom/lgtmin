import domain.Image
import services.LgtmService
import util.AppUtil
import util.AuthorizedUsers

String hash = params.hash
Image image = LgtmService.instance.getImage(hash)
def githubUsername = session.getAttribute('githubUsername')

if (githubUsername) {
    def myList = LgtmService.instance.getUserList(githubUsername)
    if (myList.hashes.contains(hash)) {
        request.setAttribute('favorite', 'true')
    }
}

log.info "Image: ${image}"

if (image) {
    request.setAttribute 'image', image
    request.setAttribute 'allowDelete', (githubUsername in AuthorizedUsers.allowDelete)
    request.setAttribute 'appUtil', AppUtil.instance
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "GET");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    if (request.getHeader('Accept')?.contains('application/json')) {
        response.setHeader("Content-Type", "application/json");
        out.write(AppUtil.instance.patchUrl(image.toJson(), request))
    } else {
        response.setHeader("Content-Type", "text/html");
        request.setAttribute('comments', true)
        forward '/WEB-INF/pages/show.html.gtpl'
    }
} else {
    redirect('/')
}
