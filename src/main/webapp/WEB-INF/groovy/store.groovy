import domain.Image
import domain.UniqueConstraintViolatedException
import domain.ValidationException
import util.AppUtil

log.info "Setting attributes"

log.info "Params: ${params}"
String imageUrl = params.imageUrl
log.info "Image Url saved will be ${imageUrl}"

def newImage = new Image(imageUrl: imageUrl)
try {
    newImage.validate()
    newImage.save()
    AppUtil.instance.evictCache(AppUtil.TOP_IMAGES)
    AppUtil.instance.evictCache(AppUtil.COUNT)
    request.setAttribute 'image', newImage
    request.setAttribute 'dataUrl', newImage.dataUrl
    response.setHeader("Content-Type", "text/html");
    redirect "/i/${newImage.hash}"

} catch (UniqueConstraintViolatedException e) {
    request.setAttribute('message', 'That image was already uploaded.')
    redirect "/i/${e.hash}"
} catch (ValidationException e) {
    request.setAttribute 'message', e.message
    request.setAttribute 'imageUrl', imageUrl
    response.setHeader "Content-Type", "text/html"
    forward '/WEB-INF/pages/upload.gtpl'
}