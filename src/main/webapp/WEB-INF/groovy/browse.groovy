import services.LgtmService
import util.AppUtil

def imageList = LgtmService.instance.imageList.blockingGet()
def PAGESIZE = 32
def page = 1
if (params.page && params.page.toString().isInteger()) {
    page = params.page as int
}
def start = (page - 1) * PAGESIZE
def stop = Math.min(start + PAGESIZE - 1, imageList.size() - 1)

if (start <= stop) {
    request.setAttribute 'imageList', imageList[start..stop]
    request.setAttribute 'appUtil', AppUtil.instance
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
} else {
    response.sendError(404)
}
