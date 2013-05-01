import groovyx.gaelyk.spock.*
import util.Shortener

class ShortenerSpec extends ConventionalGaelykUnitSpec {

  def "the shortener should loyally encode and decode numbers from 1 to 10000"() {
    given: "the initialised groovlet is invoked and data is persisted"
    def shortener = new Shortener()

    for (int i = 1; i <= 10000; i ++) {
      def code = shortener.encode(i)
      def num = shortener.decode(code)
      assert num == i
      println "${i} == ${code}"
    }

  }

}

