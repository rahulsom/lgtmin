import domain.letsencrypt.Challenge
import org.apache.http.HttpStatus
import util.AuthorizedUsers
import util.GithubAuthUtil

def github = new GithubAuthUtil(request, response)
if (github.isAuthenticated() && github.username in AuthorizedUsers.allowDelete) {
  def challengeText = request.getParameter('challengeText')
  def responseText = request.getParameter('responseText')

  new Challenge(challengeText: challengeText, responseText: responseText).save()
  response.sendRedirect('/letsencrypt/challenges')
} else {
  response.sendError(HttpStatus.SC_UNAUTHORIZED)
}