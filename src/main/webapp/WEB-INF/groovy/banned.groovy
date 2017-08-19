import domain.UserList
import util.AuthorizedUsers
import util.GithubAuthUtil

def githubAuthUtil = new GithubAuthUtil(request, response)
githubAuthUtil.withValidUser('/banned', AuthorizedUsers.allowDelete) {
    def ul = UserList.findBanned()
    request.setAttribute "userList", ul
    log.info "UL: $ul"
    forward '/WEB-INF/pages/users.gtpl'
}
