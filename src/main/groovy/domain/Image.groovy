package domain

import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestFactory
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import groovy.json.JsonBuilder
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore
import util.AppUtil
import util.Shortener

/**
 * Represents an Image
 * @author rahulsomasunderam
 */
@Entity(unindexed = false)
@ToString
class Image implements Serializable {
  String imageUrl
  long impressions = 0
  long likes = 0
  long dislikes = 0
  long credits = 100

  /**
   * Gets the hash of the id
   * @return String representing the hash
   */
  @Ignore
  String getHash() {
    def newId = (this.id as long) + 1000
    Shortener.instance.encode(newId)
  }

  /**
   * The root of the server
   * @return the root
   */
  @Ignore
  private String getRoot() {
    AppUtil.instance.root
  }

  /**
   * The url for data on the image
   * @return the url (string)
   */
  @Ignore
  String getDataUrl() {
    "${root}/i/${hash}"
  }

  /**
   * The url for upvoting the image
   * @return the url (string)
   */
  @Ignore
  String getUpvoteUrl() {
    "${root}/u/${hash}"
  }

  /**
   * The url for reporting the image
   * @return the url (string)
   */
  @Ignore
  String getReportUrl() {
    "${root}/r/${hash}"
  }

  /**
   * The markdown for embedding the image in Github
   * @return The markdown (String)
   */
  @Ignore
  String getMarkdown() {
		"""[![LGTM]($imageUrl)]($dataUrl)

[:+1:]($upvoteUrl) [:-1:]($reportUrl)"""
  }

  /**
   * A json representation of the image
   * @return json string
   */
  @Ignore
  String toJson() {
    def props = this.properties.findAll { k, v -> !(k in ['metaClass', '$key', 'class']) }
    new JsonBuilder(props).toPrettyString()
  }

  /**
   * Updates the credits based on impressions, likes and dislikes
   */
  @Ignore
  void updateCredits() {
    credits = 100 - impressions + 10 * likes - 100 * dislikes
  }

  /**
   * Updates the credits based on impressions, likes and dislikes
   */
  @Ignore
  boolean validate() {
		if (imageUrl.count(':') > 1) {
			throw new RuntimeException("URL cannot have multiple colons")
		}
		Image existingImage = Image.findByUrl(imageUrl)
		if (existingImage) {
			throw new UniqueConstraintViolatedException(existingImage.hash)
		}

		def validProtocol = imageUrl.startsWith('http://') || imageUrl.startsWith('https://')
		if (!validProtocol) {
			throw new RuntimeException("Only http and https urls allowed")
		}

		def HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new MyInit());
		def ct = requestFactory.buildHeadRequest(new GenericUrl(imageUrl)).execute().contentType
		if (!ct.startsWith('image')) {
			throw new RuntimeException("Resource at url is not an image.")
		}

		return true
  }

	private static class MyInit implements HttpRequestInitializer {
		@Override
		public void initialize(HttpRequest request) {
		}
	}
  /**
   * Uses a hash to find the Image
   * @param hash hash representing the id or image
   * @return the image
   */
  static Image findByHash(String hash) {
    def theId = Shortener.instance.decode(hash) - 1000
    Image.get(theId)
  }

  /**
   * Finds image by image url
   * @param url the image url
   * @return the image or null
   */
  static Image findByUrl(String url) {
    Image.find { where imageUrl == url }
  }

  /**
   * Lists images sorted by credits
   * @param max max number of images to return
   * @param theOffset offset for search
   * @return a list of images
   */
  static List<Image> listSortedByCredits(int max, int theOffset = 0) {
    Image.findAll {
      sort 'desc' by 'credits'
      limit max
      offset theOffset
    }
  }

}
