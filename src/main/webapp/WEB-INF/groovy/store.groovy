import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestFactory
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import domain.Image

log.info "Setting attributes"

log.info "Params: ${params}"
String imageUrl = params.imageUrl
log.info "Image Url saved will be ${imageUrl}"

if (imageUrl.startsWith('http://') || imageUrl.startsWith('https://')) {
  Image existingImage = Image.findByUrl(imageUrl)
  if (existingImage) {
    request.setAttribute('message', 'That image was already uploaded.')
    redirect "/i/${existingImage.hash}"
  } else {
    def HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
          @Override
          public void initialize(HttpRequest request) {
          }
        });

    def ct = requestFactory.buildHeadRequest(new GenericUrl(imageUrl)).execute().contentType
    if (ct.startsWith('image')) {
      def newImage = new Image()
      newImage.imageUrl = imageUrl
      newImage.save()
      request.setAttribute 'image', newImage
      request.setAttribute 'dataUrl', newImage.dataUrl
      response.setHeader("Content-Type", "text/html");
      redirect "/i/${newImage.hash}"
    } else {
      request.setAttribute 'message', "Resource at specified url was not an image"
      request.setAttribute 'imageUrl', imageUrl
      response.setHeader "Content-Type", "text/html"
      forward '/WEB-INF/pages/upload.gtpl'
    }
  }
} else {
  request.setAttribute 'message', "Only http and https urls are valid"
  request.setAttribute 'imageUrl', imageUrl
  response.setHeader "Content-Type", "text/html"
  forward '/WEB-INF/pages/upload.gtpl'
}

