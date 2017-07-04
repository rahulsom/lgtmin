import geb.spock.GebSpec
import io.github.bonigarcia.wdm.FirefoxDriverManager
import geb.spock.GebReportingSpec

class SmokeSpec extends GebReportingSpec {

    void setupSpec() {
        FirefoxDriverManager.getInstance().setup()
    }

    void "main page title should be 'LGTM.in/g'"() {
        when:
        go ''

        then:
        title == 'LGTM.in » Home'
    }

    /*void "try submitting an image"() {
        when:
        go '/g/upload'

        then:
        !$('#dataUrl')

        when:
        go '/g/upload'
        $('input[name="imageUrl"]') << 'https://31.media.tumblr.com/f52054aeea9d0f09cfadf47bd6c19992/tumblr_n6kdd61jx81qjmcfmo1_500.gif'
        $('button[type="submit"]').click()

        then:
        $('#dataUrl').text() != null

        when:
        go '/g/upload'

        then:
        !$('#dataUrl')

        when:
        $('input[name="imageUrl"]') << 'http://i.imgur.com/cSq6WCj.png'
        $('button[type="submit"]').click()

        then:
        $('#dataUrl').text() != null

        when:
        go '/g/upload'

        then:
        !$('#dataUrl')

        when:
        $('input[name="imageUrl"]') << 'http://i.imgur.com/caopBHC.jpg'
        $('button[type="submit"]').click()

        then:
        $('#dataUrl').text() != null

        when:
        go '/g'

        then:
        $('#dataUrl')
        $('#imageUrl').value() in [
                'https://31.media.tumblr.com/f52054aeea9d0f09cfadf47bd6c19992/tumblr_n6kdd61jx81qjmcfmo1_500.gif',
                'http://i.imgur.com/cSq6WCj.png',
                'http://i.imgur.com/caopBHC.jpg'
        ]

    }*/
}
