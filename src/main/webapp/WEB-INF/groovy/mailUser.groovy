import org.apache.http.HttpStatus
import util.AuthorizedUsers
import util.GithubAuthUtil

def github = new GithubAuthUtil(request, response)
if (github.isAuthenticated() && github.username in AuthorizedUsers.allowDelete) {
    def email = request.getParameter('email')
    def username = request.getParameter('username')
    def message = request.getParameter('message')

    mail.send from: "admin@lgtm.in",
            to: email,
            subject: "lgtm.in Fair Use Policy",
            textBody: message

    session.message = "Message sent"
    response.sendRedirect(request.getHeader('referer'))
} else {
    response.sendError(HttpStatus.SC_UNAUTHORIZED)
}