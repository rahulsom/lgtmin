import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore

/**
 * Created with IntelliJ IDEA.
 * User: rahulsomasunderam
 * Date: 4/24/13
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@ToString
class Image {
  String imageUrl
  long impressions = 0
  long likes = 0
  long dislikes = 0
  long credits = 100

  @Ignore String getHash() {
    def newId = (this.id as long) + 1000
    Shortener.instance.encode(newId)
  }

  static Image findByHash(String hash) {
    def theId = Shortener.instance.decode(hash) - 1000
    Image.get(theId)
  }
}
