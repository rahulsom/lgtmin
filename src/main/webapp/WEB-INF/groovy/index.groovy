def images = Image.findAll {
  limit 12
}

log.info "Images: ${images}"
request.setAttribute 'imageList', images
forward '/WEB-INF/pages/index.gtpl'