import domain.Image
import services.LgtmService
import util.AppUtil
import util.AuthorizedUsers

String hash = params.hash
def lgtmService = LgtmService.instance

Image image = lgtmService.getImage(hash).blockingGet()
def githubUsername = session.getAttribute('githubUsername')

if (githubUsername) {
    def myList = lgtmService.getUserList(githubUsername).blockingGet()
    if (myList.hashes.contains(hash)) {
        request.setAttribute('favorite', 'true')
    }
}

log.info "Image: ${image}"

if (image) {
    request.setAttribute 'image', image
    request.setAttribute 'allowDelete', (githubUsername in AuthorizedUsers.allowDelete)
    def appUtil = AppUtil.instance
    request.setAttribute 'appUtil', appUtil
    response.setHeader "Access-Control-Allow-Origin", "*";
    response.setHeader "Access-Control-Allow-Methods", "GET";
    response.setHeader "Access-Control-Allow-Credentials", "true";
    if (request.getHeader('Accept')?.contains('application/json')) {
        response.setHeader "Content-Type", "application/json";
        out.write(appUtil.patchUrl(image.toJson(), request))
    } else {
        response.setHeader 'Content-Type', 'text/html'
        request.setAttribute 'comments', true
        forward '/WEB-INF/pages/show.html.gtpl'
    }
} else {
    redirect '/'
}
