package util

import groovyx.gaelyk.spock.*

class ShortenerSpec extends ConventionalGaelykUnitSpec {

  def "the shortener should loyally encode and decode numbers from 1 to 10000"() {
    given: "the initialised groovlet is invoked and data is persisted"
    def shortener = Shortener.instance

    for (int i = 1; i <= 10000; i++) {
      def code = shortener.encode(i)
      def num = shortener.decode(code)
      assert num == i
    }

  }

  def "the shortener should have certain standard values"() {
    expect:
    Shortener.instance.encode(a) == b && Shortener.instance.decode(b) == a

    where:
    a     | b
    1     | '1'
    10    | 'a'
    35    | 'z'
    36    | 'A'
    61    | 'Z'
    62    | '01'
    96    | 'y1'
    136   | 'c2'
    228   | 'G3'
    985   | 'Tf'
    999   | '7g'
    3843  | 'ZZ'
    3844  | '001'
    10000 | 'iB2'

  }

}

