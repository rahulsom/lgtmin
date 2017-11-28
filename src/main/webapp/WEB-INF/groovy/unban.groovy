import services.LgtmService
import util.AuthorizedUsers
import util.GithubAuthUtil

def githubAuthUtil = new GithubAuthUtil(request, response)
githubAuthUtil.withValidUser('/banned', AuthorizedUsers.allowDelete) {
    def ul = LgtmService.instance.getUserList(params.username).blockingGet()
    ul.bannedFromUpload = Boolean.FALSE
    ul.save()

    redirect "/banned"
}