package util

import com.google.appengine.api.urlfetch.HTTPHeader
import com.google.appengine.api.urlfetch.HTTPRequest
import com.google.appengine.api.urlfetch.HTTPResponse
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Log
import groovyx.gaelyk.GaelykBindings

import static com.google.appengine.api.urlfetch.HTTPMethod.POST

@GaelykBindings
@Log
class ImgurUtil {

    private static final Properties properties = Properties.newInstance().with {
        load ImgurUtil.class.classLoader.getResourceAsStream('imgur.properties')
        it
    }

    String uploadImage(String imageUrl) {
        log.info "ImageURL: $imageUrl"
        def request = new HTTPRequest(new URL('https://api.imgur.com/3/image'), POST)
        def imgurClientId = properties.getProperty('clientId')
        request.addHeader new HTTPHeader('Authorization', "Client-ID ${imgurClientId}")
        request.addHeader new HTTPHeader('Content-Type', 'application/json')
        request.setPayload(new JsonBuilder([
                type : 'URL',
                image: imageUrl
        ]).toString().bytes)
        def resp = urlFetch.fetch(request) as HTTPResponse
        def json = new JsonSlurper().parse(resp.content)
        log.info "JSON: $json"
        return json.data.link ?: imageUrl
    }
}
