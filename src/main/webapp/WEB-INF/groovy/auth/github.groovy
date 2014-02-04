import util.GithubAuthUtil

def githubAuthUtil = new GithubAuthUtil(request, response)
if (githubAuthUtil.isAuthenticated()) {
    redirect "/"
} else {
    githubAuthUtil.forceAuthentication()
}
