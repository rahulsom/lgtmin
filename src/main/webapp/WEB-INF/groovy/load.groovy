import services.LgtmService

import domain.Image

String hash = params.hash
Image image = LgtmService.instance.getImage(hash)
if (session.getAttribute('githubUsername')) {
    def myList = LgtmService.instance.getUserList(session.getAttribute('githubUsername'))
    if (myList.hashes.contains(hash)) {
        request.setAttribute('favorite', 'true')
    }
}

log.info "Image: ${image}"

if (image) {
    request.setAttribute 'image', image
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "GET");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    if (request.getHeader('Accept')?.contains('application/json')) {
        response.setHeader("Content-Type", "application/json");
        out.write(image.toJson())
    } else {
        response.setHeader("Content-Type", "text/html");
        request.setAttribute('comments', true)
        forward '/WEB-INF/pages/show.html.gtpl'
    }
} else {
    redirect('/')
}
