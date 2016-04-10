import domain.UserList
import services.LgtmService
import util.AuthorizedUsers
import util.GithubAuthUtil

def githubAuthUtil = new GithubAuthUtil(request, response)
githubAuthUtil.withValidUser('/banned', AuthorizedUsers.allowDelete) {
  def ul = UserList.findBanned()
  request.setAttribute "users", ul
  response.writer.println ul*.username
  forward '/WEB-INF/pages/users.gtpl'
}
