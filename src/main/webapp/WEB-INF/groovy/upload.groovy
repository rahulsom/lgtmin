import services.LgtmService
import util.GithubAuthUtil

def githubAuthUtil = new GithubAuthUtil(request, response)
githubAuthUtil.withValidUser('/upload') {
  def ul = LgtmService.instance.getUserList(userName)
  request.setAttribute('banned', ul.bannedFromUpload)
  forward '/WEB-INF/pages/upload.gtpl'
}
