import util.GithubAuthUtil

def githubAuthUtil = new GithubAuthUtil(request, response)
githubAuthUtil.withValidUser('/upload') {
  forward '/WEB-INF/pages/upload.gtpl'
}
