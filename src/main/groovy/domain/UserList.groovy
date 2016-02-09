package domain

import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Unindexed

/**
 * Represents the list of images favorited by a user
 *
 * @author Rahul Somasunderam
 */
@Entity(unindexed = false)
@ToString
class UserList implements Serializable {
  String username
  @Unindexed
  List<String> hashes

  static UserList findByUsername(String un) {
    UserList.find {
      where username == un
    }
  }
}
