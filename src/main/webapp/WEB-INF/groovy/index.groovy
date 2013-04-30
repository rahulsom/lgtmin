def images = Image.findAll {
  sort 'desc' by 'credits'
  limit 12
}

log.info "Images: ${images}"
request.setAttribute 'imageList', images
forward '/WEB-INF/pages/index.gtpl'