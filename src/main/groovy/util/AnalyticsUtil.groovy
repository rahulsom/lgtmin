package util

import groovy.util.logging.Log

import javax.servlet.http.HttpServletRequest
import java.util.logging.Level

/**
 * Created with IntelliJ IDEA.
 * User: rahulsomasunderam
 * Date: 5/8/13
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Log
class AnalyticsUtil {
    static def sendInfo(HttpServletRequest theRequest, String hash = null) {

        def urlString = "https://ssl.google-analytics.com/collect"
        def body = [
                v: 1,             // Version.
                tid: AnalyticsUtil.TrackingId,  // Tracking ID / Web property / Property ID.
                cid: theRequest.remoteAddr,        // Anonymous Client ID.

                t: 'pageview',     // Pageview hit type.
                dh: 'www.lgtm.in',  // Document hostname.
                dp: hash ?: '/g.json',       // Page.
                dt: 'Random Fetch',    // Title.

        ]
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
            def recaptchaResponse = new InputStreamReader(connection.inputStream).text
            println(recaptchaResponse)
        } catch (Exception e) {
            log.log(Level.WARNING, "Exception occurred while sending analytics to GA", e)
        }

    }

    public static final TrackingId = 'UA-40490747-1'

}
