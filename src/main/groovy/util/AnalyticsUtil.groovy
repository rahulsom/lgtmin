package util

import groovy.util.logging.Log

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.logging.Level

/**
 * Adds support for GA when there isn't any javascript running on the page.
 * @author rahulsomasunderam
 */
@Log
class AnalyticsUtil {

    public static final int SecondsInDay = 60 * 60 * 24

    public static final String DoNotTrackHeader = "DNT"
    public static final String DoNotTrackValue = "1"

    static void sendInfo(HttpServletRequest theRequest, HttpServletResponse response, String hash, String title) {

        if (theRequest.getHeader(DoNotTrackHeader) == DoNotTrackValue) {
            log.warning "User requested to not track them"
        }
        def urlString = "https://ssl.google-analytics.com/collect"

        log.info "Headers: ${theRequest.getHeaderNames().toList().toString()}"
        def ua = theRequest.getHeader('User-Agent')
        def ul = theRequest.getHeader('Accept-Language')?.toLowerCase()?.split(',')?.getAt(0)
        log.info "Cookies: ${theRequest.cookies.collect { "${it.name}=${it.value}(${it.maxAge})" }.join(',')}"
        String cid
        if (theRequest.cookies.find { it.name == '_ga' }) {
            cid = theRequest.cookies.find { it.name == '_ga' }.value
        } else if (theRequest.cookies.find { it.name == 'LongSession' }) {
            cid = theRequest.cookies.find { it.name == 'LongSession' }.value
        } else {
            cid = UUID.randomUUID().toString()
            response.addCookie(new Cookie('LongSession', cid).with {
                maxAge = SecondsInDay * 365 * 2
                it
            })
        }
        def body = [
                v  : 1,                         // Version.
                tid: AnalyticsUtil.TrackingId,  // Tracking ID / Web property / Property ID.
                cid: cid,                       // Anonymous Client ID.

                uip: theRequest.remoteAddr,     // IP Address
                ua : ua,                        // User Agent

                t  : 'pageview',                // Pageview hit type.
                dh : 'www.lgtm.in',             // Document hostname.
                dp : hash ?: '/g.json',         // Page.
                dt : title,                     // Title.
        ]

        if (ul) {
            body.ul = ul
        }

        log.info(body.toMapString())
        def queryString = body.collect { k, v ->
            "${k.toString()}=${v.toString().replace(' ', '+')}"
        }.join('&')

        def url = new URL(urlString)
        def connection = url.openConnection()
        connection.setRequestMethod("POST")
        connection.doOutput = true

        def writer = new OutputStreamWriter(connection.outputStream)
        writer.write(queryString)
        writer.flush()
        writer.close()
        connection.connect()

        try {
            def analyticsResponse = new InputStreamReader(connection.inputStream).text
            log.info analyticsResponse
        } catch (Exception e) {
            log.log(Level.WARNING, "Exception occurred while sending analytics to GA", e)
        }

    }

    public static final TrackingId = 'UA-40490747-1'

}
