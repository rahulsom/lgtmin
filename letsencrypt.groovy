import groovy.time.TimeCategory
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import it.zero11.acme.Acme
import it.zero11.acme.AcmeChallengeListener
import it.zero11.acme.storage.impl.DefaultCertificateStorage
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request

@GrabResolver('https://jitpack.io')
@Grab('com.github.zero11it:acme-client:42af8c2c06')
@Grab('org.glassfish.jersey.core:jersey-client:2.25.1')
@Grab('com.squareup.okhttp3:okhttp:3.6.0')
@Grab('org.gebish:geb-core:1.1.1')

final String CA_STAGING_URL = 'https://acme-staging.api.letsencrypt.org/acme'
final String CA_PRODUCTION_URL = 'https://acme-v01.api.letsencrypt.org/acme'
final String AGREEMENT_URL = 'https://letsencrypt.org/documents/LE-SA-v1.1.1-August-1-2016.pdf'

def jwtKey = System.getenv('JWT_SIGNING_KEY')

def expiry = use(TimeCategory) {
    new Date() + 2.minutes
}

def jwtToken = Jwts.builder().
        setSubject('rahulsom').
        setExpiration(expiry).
        setNotBefore(new Date()).
        signWith(SignatureAlgorithm.HS512, jwtKey.bytes).
        compact()

String port = "22"
String[] domains = 'lgtm.in,www.lgtm.in'.split(",")

AcmeChallengeListener challengeListener = new AcmeChallengeListener() {
    @Override
    boolean challengeHTTP01(String domain, String token, String challengeURI, String challengeBody) {
        println "Domain             : $domain"
        println "Token              : $token"
        println "Challenge URI      : $challengeURI"
        println "Challenge Body     : $challengeBody"

        def client = new OkHttpClient()
        def request = new Request.Builder().
                url('http://lgtm.in/letsencrypt/save').
                headers(
                        new Headers.Builder().
                                add('Authorization', "Bearer ${jwtToken}").
                                add('Accept', 'application/json').
                                build()
                ).
                post(
                        new FormBody.Builder().
                                add('challengeText', token).
                                add('responseText', challengeBody).
                                build()
                ).
                build()

        def resp = client.newCall(request).execute()
        println resp.body().toString()

        return true
    }

    @Override
    void challengeCompleted(String domain) {
        println "Challenge Completed: $domain"
    }

    @Override
    void challengeFailed(String domain) {
        println "Challenge Failed   : $domain"
    }
}

Acme acme = new Acme(CA_PRODUCTION_URL, new DefaultCertificateStorage(true), true, false)
acme.getCertificate(domains, AGREEMENT_URL, ['mailto:rahul.som@gmail.com'] as String[], challengeListener)
