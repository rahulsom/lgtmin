package util

import groovyx.gaelyk.spock.*

class ShortenerSpec extends ConventionalGaelykUnitSpec {

  def "the shortener should loyally encode and decode numbers from 1 to 10000"() {
    given: "the initialised groovlet is invoked and data is persisted"
    def shortener = Shortener.instance

    for (int i = 1; i <= 10000; i ++) {
      def code = shortener.encode(i)
      def num = shortener.decode(code)
      assert num == i
      println "${i} == ${code}"
    }

  }

}

