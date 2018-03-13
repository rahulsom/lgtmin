import domain.Image
import domain.UniqueConstraintViolatedException
import domain.UserList
import domain.ValidationException
import services.LgtmService
import util.AppUtil
import util.GithubAuthUtil
import util.ImgurUtil

log.info "Setting attributes"

log.info "Params: ${params}"
String imageUrl = params.imageUrl
log.info "Image Url saved will be ${imageUrl}"

def githubAuthUtil = new GithubAuthUtil(request, response)
response.setHeader 'Strict-Transport-Security', 'max-age=300; preload'
if (githubAuthUtil.isAuthenticated()) {
    if (!imageUrl.startsWith('https://i.imgur.com')) {
        imageUrl = new ImgurUtil().uploadImage(imageUrl)
    }
    def username = session.getAttribute(GithubAuthUtil.GITHUB_USERNAME) as String
    def newImage = new Image(
            imageUrl: imageUrl,
            uploader: username,
            uploaderEmail: session.getAttribute(GithubAuthUtil.GITHUB_EMAIL_PRIMARY)
    )
    UserList myList = LgtmService.instance.getUserList(username).blockingGet()
    try {

        if (myList.bannedFromUpload) {
            request.setAttribute 'message', "You're not allowed to upload images"
            redirect '/'
        } else {
            newImage.validate()
            newImage.save()
            request.setAttribute 'image', newImage
            request.setAttribute 'dataUrl', newImage.dataUrl

            if (!myList.hashes.contains(newImage.hash)) {
                myList.hashes.add newImage.hash
            }
            myList.save()
            AppUtil.instance.store "/l/${username}", myList

            response.setHeader "Content-Type", "text/html"
            redirect "/i/${newImage.hash}"
        }
    } catch (UniqueConstraintViolatedException e) {
        request.setAttribute 'message', 'That image was already uploaded.'
        redirect "/i/${e.hash}"
    } catch (ValidationException e) {
        request.setAttribute 'banned', myList.bannedFromUpload
        request.setAttribute 'message', e.message
        request.setAttribute 'imageUrl', imageUrl
        response.setHeader "Content-Type", "text/html"
        forward '/WEB-INF/pages/upload.gtpl'
    }
} else {
    request.setAttribute 'message', "You're not logged in yet!"
    request.setAttribute 'imageUrl', imageUrl
    response.setHeader "Content-Type", "text/html"
    forward '/WEB-INF/pages/upload.gtpl'
}
