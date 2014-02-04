import util.GithubAuthUtil

def githubAuthUtil = new GithubAuthUtil(request, response)
githubAuthUtil.validateUser()
