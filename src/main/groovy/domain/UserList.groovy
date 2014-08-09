package domain

import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore

/**
 * Created by rahulsomasunderam on 8/9/14.
 */
@Entity(unindexed = false)
@ToString
class UserList {
    String username
    List<String> hashes

    static UserList findByUsername(String un) {
        UserList.find {
            where username == un
        }
    }
}
