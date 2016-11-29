import domain.UserList
import services.LgtmService

String userName = params.username
UserList myList = LgtmService.instance.getUserList(userName)
def imageList = LgtmService.instance.imageList.findAll { img -> myList.hashes && myList.hashes.contains(img.hash) }

static final PAGESIZE = 32
final int page = params.page ? Integer.parseInt(params.page) : 1
final int start = (page - 1) * PAGESIZE
final int stop = Math.min(start + PAGESIZE - 1, imageList.size() - 1)
log.info "Start: $start, Stop: $stop, ilS: ${imageList.size()}"
request.setAttribute 'imageList', imageList[start..stop]
request.setAttribute 'username', userName
if (stop + 1 < imageList.size()) {
  request.setAttribute 'next', page + 1
}
if (start > 0) {
  request.setAttribute 'prev', page - 1
}

if (params.page != null) {
  forward '/WEB-INF/pages/browse.gtpl'
} else {
  forward '/WEB-INF/pages/browsePage.gtpl'
}
