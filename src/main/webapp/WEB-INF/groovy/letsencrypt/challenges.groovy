import domain.letsencrypt.Challenge
import util.AuthorizedUsers
import util.GithubAuthUtil

def githubAuthUtil = new GithubAuthUtil(request, response)
githubAuthUtil.withValidUser('/banned', AuthorizedUsers.allowDelete) {
  request.setAttribute 'challenges', Challenge.findAll()
  forward('/WEB-INF/pages/letsencrypt/challenges.gtpl')
}