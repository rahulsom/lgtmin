import services.LgtmService


def imageList = LgtmService.instance.imageList
request.setAttribute 'imageList', imageList
forward '/WEB-INF/pages/browse.gtpl'