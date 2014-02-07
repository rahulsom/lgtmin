import geb.spock.GebSpec

class SmokeSpec extends GebSpec {
    void "main page title should be 'LGTM.in/g'"() {
        when:
        go ''

        then:
        title == 'LGTM.in/g'
    }
}
