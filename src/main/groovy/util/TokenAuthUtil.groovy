package util

import groovy.time.TimeCategory
import io.jsonwebtoken.*

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthUtil {

    HttpServletRequest request
    HttpServletResponse response
    Map params

    TokenAuthUtil(HttpServletRequest request, HttpServletResponse response) {
        this.request = request
        this.response = response
        params = request.getParameterMap()
    }

    private String getAuthHeader() { request.getHeader('Authorization') }

    private String getBearerToken() {
        (authHeader.startsWith('Bearer') && authHeader.split(' ').length == 2) ?
                authHeader.split(' ')[1] : null
    }

    boolean isAuthenticated() {
        def claims = jwt?.body as Claims
        println claims
        def now = new Date()
        claims.getNotBefore() < now && claims.getExpiration() > now && claims.getSubject() != null
    }

    String getUsername() {
        isAuthenticated() ? (jwt?.body as Claims)?.getSubject() : null
    }

    private Jwt getJwt() {
        if (!bearerToken) return null
        try {
            def key = properties.getProperty('key')
            Jwts.parser().setSigningKey(key.bytes).parse(bearerToken)
        } catch (SignatureException e) {
            return null
        }
    }

    private static final Properties properties = Properties.newInstance().with {
        load TokenAuthUtil.class.classLoader.getResourceAsStream('jwt.properties')
        it
    }

    String generateToken(String username) {
        def expiry = use(TimeCategory) {
            new Date() + 30.minutes
        }
        def key = properties.getProperty('key')

        Jwts.builder().
                setSubject(username).
                setExpiration(expiry).
                setNotBefore(new Date()).
                signWith(SignatureAlgorithm.HS512, key.bytes).
                compact()
    }
}
