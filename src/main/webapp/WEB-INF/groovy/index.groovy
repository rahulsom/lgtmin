import domain.Image

def imageList = Image.listSortedByCredits(12)

log.info "Images: ${imageList}"
request.setAttribute 'imageList', imageList
forward '/WEB-INF/pages/index.gtpl'