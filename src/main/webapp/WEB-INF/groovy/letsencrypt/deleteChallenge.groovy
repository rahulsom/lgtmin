import domain.letsencrypt.Challenge
import org.apache.http.HttpStatus
import util.AuthorizedUsers
import util.GithubAuthUtil

def github = new GithubAuthUtil(request, response)
if (github.isAuthenticated() && github.username in AuthorizedUsers.allowDelete) {
    def challenge = Challenge.get(params.id.toLong())
    challenge.delete()
    response.sendRedirect('/letsencrypt/challenges')
} else {
    response.sendError(HttpStatus.SC_UNAUTHORIZED)
}
