package domain

import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity

/**
 * Created by rahulsomasunderam on 8/9/14.
 */
@Entity(unindexed = false)
@ToString
class UserList implements Serializable {
    String username
    List<String> hashes

    static UserList findByUsername(String un) {
        UserList.find {
            where username == un
        }
    }
}
