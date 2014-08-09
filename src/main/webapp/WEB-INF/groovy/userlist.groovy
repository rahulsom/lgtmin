import domain.UserList
import services.LgtmService

String userName = params.username
UserList myList = LgtmService.instance.getUserList(userName)
def imageList = LgtmService.instance.imageList.findAll { img ->
    myList.hashes && myList.hashes.contains(img.hash)
}

request.setAttribute 'imageList', imageList
forward '/WEB-INF/pages/browse.gtpl'