import domain.Image

def imageList = Image.findAll {
  sort 'desc' by 'credits'
  limit 12
}

log.info "Images: ${imageList}"
request.setAttribute 'imageList', imageList
forward '/WEB-INF/pages/index.gtpl'