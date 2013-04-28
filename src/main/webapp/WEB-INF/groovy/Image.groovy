import groovy.json.JsonBuilder
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

  static Image findByHash(String hash) {
    def theId = Shortener.instance.decode(hash) - 1000
    Image.get(theId)
  }

  static Image findByUrl(String url) {
    Image.find { where imageUrl == url }
  }

  @Ignore String getHash() {
    def newId = (this.id as long) + 1000
    Shortener.instance.encode(newId)
  }

  @Ignore String getDataUrl() {
    "http://www.lgtm.in/i/${hash}"
  }

  @Ignore String getUpvoteUrl() {
    "http://www.lgtm.in/u/${hash}"
  }

  @Ignore String getReportUrl() {
    "http://www.lgtm.in/r/${hash}"
  }

  @Ignore String getMarkdown() {
    "[![LGTM](${imageUrl})](${dataUrl})\\n" +
        "[:+1:](${upvoteUrl}) [:-1:](${reportUrl})"
  }

  @Ignore String toJson() {
    def props = this.properties.findAll {k,v -> !(k in ['metaClass', '$key', 'class'])}
    new JsonBuilder(props).toPrettyString()
  }
}
