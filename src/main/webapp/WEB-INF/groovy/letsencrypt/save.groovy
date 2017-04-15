import domain.letsencrypt.Challenge
import groovy.json.JsonBuilder
import org.apache.http.HttpStatus
import util.AuthorizedUsers
import util.GithubAuthUtil
import util.TokenAuthUtil

def github = new GithubAuthUtil(request, response)
def jwt = new TokenAuthUtil(request, response)

def isAuthenticated = github.authenticated || jwt.authenticated
def username =
        github.authenticated ? github.username :
                jwt.authenticated ? jwt.username :
                        null

if (isAuthenticated && username in AuthorizedUsers.allowDelete) {
    def challengeText = request.getParameter('challengeText')
    def responseText = request.getParameter('responseText')

    def newChallenge = new Challenge(challengeText: challengeText, responseText: responseText)
    newChallenge.save()
    if (headers['Accept'] == 'application/json') {
        response.setContentType('application/json')
        out.write(new JsonBuilder([id: newChallenge.id]))
    } else {
        response.sendRedirect('/letsencrypt/challenges')
    }
} else {
    response.sendError(HttpStatus.SC_UNAUTHORIZED)
}