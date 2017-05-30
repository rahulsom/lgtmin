import services.LgtmService
import util.AppUtil

def imageList = LgtmService.instance.topImages

log.info "Images: ${imageList}"
request.setAttribute 'imageList', imageList
request.setAttribute 'appUtil', AppUtil.instance
forward '/WEB-INF/pages/index.gtpl'