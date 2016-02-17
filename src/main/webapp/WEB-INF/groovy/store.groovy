import domain.Image
import domain.UniqueConstraintViolatedException
import domain.UserList
import domain.ValidationException
import services.LgtmService
import util.GithubAuthUtil

log.info "Setting attributes"

log.info "Params: ${params}"
String imageUrl = params.imageUrl
log.info "Image Url saved will be ${imageUrl}"

def githubAuthUtil = new GithubAuthUtil(request, response)
if (githubAuthUtil.isAuthenticated()) {
  def username = session.getAttribute(GithubAuthUtil.GITHUB_USERNAME) as String
  def newImage = new Image(
      imageUrl: imageUrl,
      uploader: username,
      uploaderEmail: session.getAttribute(GithubAuthUtil.GITHUB_EMAIL_PRIMARY)
  )
  try {
    newImage.validate()
    newImage.save()
    request.setAttribute 'image', newImage
    request.setAttribute 'dataUrl', newImage.dataUrl

    UserList myList = LgtmService.instance.getUserList(username)
    if (!myList.hashes.contains(newImage.hash)) {
      myList.hashes.add(newImage.hash)
    }
    myList.save()

    response.setHeader "Content-Type", "text/html";
    redirect "/i/${newImage.hash}"
  } catch (UniqueConstraintViolatedException e) {
    request.setAttribute 'message', 'That image was already uploaded.'
    redirect "/i/${e.hash}"
  } catch (ValidationException e) {
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
