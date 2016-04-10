package util

import com.google.api.client.http.*
import com.google.api.client.http.javanet.NetHttpTransport
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Log
import groovyx.gaelyk.GaelykBindings

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * Manages Github Authentication
 *
 * @author Rahul Somasunderam
 */
@GaelykBindings
@Log
class GithubAuthUtil {

  public static final String GITHUB_STATE = 'githubState'
  public static final String POST_LOGIN_URI = 'postLoginUri'
  public static final String GITHUB_USERNAME = 'githubUsername'
  public static final String GITHUB_AVATAR = 'githubAvatar'
  public static final String GITHUB_TOKEN = 'githubToken'
  public static final String GITHUB_EMAIL_PRIMARY = 'primaryEmail'

  HttpServletRequest request
  HttpServletResponse response
  HttpSession session
  Map params

  GithubAuthUtil(HttpServletRequest request, HttpServletResponse response) {
    this.request = request
    this.response = response
    session = request.getSession(true)
    params = request.getParameterMap()
  }

  boolean isAuthenticated() {
    session?.getAttribute(GITHUB_USERNAME)
  }

  String getUsername() {
    session?.getAttribute(GITHUB_USERNAME)
  }

  void forceAuthentication() {
    def params = [
        client_id   : clientId,
        redirect_uri: "${AppUtil.instance.root}/auth/me",
        state       : UUID.randomUUID().toString(),
        scope       : 'user:email'
    ]

    def session = request.getSession(true)
    session.setAttribute GITHUB_STATE, params.state
    response.sendRedirect "https://github.com/login/oauth/authorize?${params.collect { k, v -> "$k=$v" }.join('&')}"
  }

  void withValidUser(String uri, Closure c) {
    if (isAuthenticated()) {
      c.call()
    } else {
      session.setAttribute POST_LOGIN_URI, uri
      forceAuthentication()
    }
  }

  void withValidUser(String uri, List<String> acceptedUsers, Closure c) {
    if (isAuthenticated() && acceptedUsers.contains(username)) {
      c.call()
    } else {
      session.setAttribute POST_LOGIN_URI, uri
      forceAuthentication()
    }
  }

  def isValidOAuthRequest() {
    params.state == session?.getAttribute(GITHUB_STATE)
  }

  void validateUser() {
    if (!isValidOAuthRequest()) {
      response.sendRedirect '/auth/github'
    }

    def HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new MyInit('application/json'));
    try {
      def oAuthJson = getUserToken(requestFactory)
      session.setAttribute GITHUB_TOKEN, oAuthJson.access_token

      def userJson = getUser(requestFactory, oAuthJson.access_token as String)
      log.info "User was: ${new JsonBuilder(userJson)}"
      session.setAttribute GITHUB_USERNAME, userJson.login
      session.setAttribute GITHUB_AVATAR, userJson.avatar_url
      session.setAttribute "isAdmin", AuthorizedUsers.allowDelete.contains(userJson.login)

      def userEmails = getEmails(requestFactory, oAuthJson.access_token as String)
      session.setAttribute GITHUB_EMAIL_PRIMARY, userEmails.find {it.primary}.email
      log.info "Primary email: ${session.getAttribute(GITHUB_EMAIL_PRIMARY)}"

      log.info "'${session.getAttribute(GITHUB_USERNAME)}' logged in."
      if (session.getAttribute(POST_LOGIN_URI)) {
        def postLoginUri = session.getAttribute(POST_LOGIN_URI)
        session.removeAttribute POST_LOGIN_URI
        response.sendRedirect postLoginUri
      } else {
        response.sendRedirect "/"
      }
    } catch (HttpResponseException e) {
      // TODO Could not log in
      response.sendRedirect '/'
    }

  }

  private Map<String,Object> getUser(HttpRequestFactory requestFactory, String accessToken) {
    def userResponse = requestFactory.buildGetRequest(
        new GenericUrl("https://api.github.com/user?access_token=${accessToken}")
    ).execute()
    new JsonSlurper().parse(userResponse.content.newReader()) as Map<String,Object>
  }

  private List<Map<String,Object>> getEmails(HttpRequestFactory requestFactory, String accessToken) {
    def userResponse = requestFactory.buildGetRequest(
        new GenericUrl("https://api.github.com/user/emails?access_token=${accessToken}")
    ).execute()
    new JsonSlurper().parse(userResponse.content.newReader()) as List<Map<String,Object>>
  }

  private Map<String,Object>  getUserToken(HttpRequestFactory requestFactory) {
    def oAuthTokenResponse = requestFactory.buildPostRequest(
        new GenericUrl('https://github.com/login/oauth/access_token'),
        new UrlEncodedContent([
            client_id    : clientId,
            client_secret: clientSecret,
            code         : params.code
        ])
    ).execute()
    new JsonSlurper().parse(oAuthTokenResponse.content.newReader()) as Map<String, Object>
  }

  private static final Properties properties = Properties.newInstance().with {
    load GithubAuthUtil.class.classLoader.getResourceAsStream('oauth.properties')
    it
  }

  /**
   * Client ID for OAuth
   * @return
   */
  static String getClientId() {
    properties?.get('clientId') ?: 'CLIENT_ID'
  }

  /**
   * Client Secret for OAuth
   * @return
   */
  static String getClientSecret() {
    properties?.get('clientSecret') ?: 'SECRET'
  }
}
