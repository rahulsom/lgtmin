import services.LgtmService

def imageList = LgtmService.instance.topImages

log.info "Images: ${imageList}"
request.setAttribute 'imageList', imageList
forward '/WEB-INF/pages/index.gtpl'