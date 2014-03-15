package util

import com.google.api.client.http.*
import com.google.api.client.http.javanet.NetHttpTransport
import groovy.json.JsonSlurper
import groovy.util.logging.Log
import groovyx.gaelyk.GaelykBindings

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * Created by rahulsomasunderam on 2/3/14.
 */
@GaelykBindings
@Log
class GithubAuthUtil {

    public static final String GITHUB_STATE = 'githubState'
    public static final String POST_LOGIN_URI = 'postLoginUri'
    public static final String GITHUB_USERNAME = 'githubUsername'
    public static final String GITHUB_AVATAR = 'githubAvatar'
    public static final String GITHUB_TOKEN = 'githubToken'

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
                client_id: clientId,
                redirect_uri: "${AppUtil.instance.root}/auth/me",
                state: UUID.randomUUID().toString()
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
            def oAuthTokenResponse = requestFactory.buildPostRequest(
                    new GenericUrl('https://github.com/login/oauth/access_token'),
                    new UrlEncodedContent([
                            client_id: clientId,
                            client_secret: clientSecret,
                            code: params.code
                    ])
            ).execute()
            def oAuthJson = new JsonSlurper().parse(oAuthTokenResponse.content.newReader())
            session.setAttribute GITHUB_TOKEN, oAuthJson.access_token

            def userResponse = requestFactory.buildGetRequest(
                    new GenericUrl("https://api.github.com/user?access_token=${oAuthJson.access_token}")
            ).execute()
            def userJson = new JsonSlurper().parse(userResponse.content.newReader())
            log.info "User was: ${userJson}"
            session.setAttribute GITHUB_USERNAME, userJson.login
            session.setAttribute GITHUB_AVATAR, userJson.avatar_url

        } catch (HttpResponseException e) {
            response.sendRedirect '/'
        }

        if (session.getAttribute(POST_LOGIN_URI)) {
            def postLoginUri = session.getAttribute(POST_LOGIN_URI)
            session.removeAttribute POST_LOGIN_URI
            response.sendRedirect postLoginUri
        } else {
            response.sendRedirect "/"
        }

    }

    private static final Properties properties = Properties.newInstance().with {
        load(GithubAuthUtil.classLoader.getResourceAsStream('oauth.properties'))
        it
    }

    /**
     * Client ID for OAuth
     * @return
     */
    String getClientId() {
        properties?.getAt('clientId') ?: 'CLIENT_ID'
    }

    /**
     * Client Secret for OAuth
     * @return
     */
    String getClientSecret() {
        properties?.getAt('clientSecret') ?: 'SECRET'
    }
}
