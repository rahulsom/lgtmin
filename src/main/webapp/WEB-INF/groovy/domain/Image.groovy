package domain

import groovy.json.JsonBuilder
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore
import util.AppUtil
import util.Shortener

/**
 * Created with IntelliJ IDEA.
 * User: rahulsomasunderam
 * Date: 4/24/13
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity(unindexed=false)
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
  
  @Ignore private String getRoot() {
    AppUtil.instance.root
  }

  @Ignore String getDataUrl() {
    "${root}/i/${hash}"
  }

  @Ignore String getUpvoteUrl() {
    "${root}/u/${hash}"
  }

  @Ignore String getReportUrl() {
    "${root}/r/${hash}"
  }

  @Ignore String getMarkdown() {
    "[![LGTM](${imageUrl})](${dataUrl})\\n" +
        "[:+1:](${upvoteUrl}) [:-1:](${reportUrl})"
  }

  @Ignore String toJson() {
    def props = this.properties.findAll {k,v -> !(k in ['metaClass', '$key', 'class'])}
    new JsonBuilder(props).toPrettyString()
  }

  @Ignore
  void updateCredits() {
    credits = 100 - impressions + 10 * likes - 100 * dislikes
  }
}
