import org.apache.http.HttpStatus
import util.AuthorizedUsers
import util.GithubAuthUtil
import util.TokenAuthUtil

def github = new GithubAuthUtil(request, response)
def jwt = new TokenAuthUtil(request, response)

if (github.isAuthenticated() && github.username in AuthorizedUsers.allowDelete) {
    response.setContentType('text/plain')
    out.print jwt.generateToken(github.username)
} else {
    response.sendError(HttpStatus.SC_UNAUTHORIZED)
}