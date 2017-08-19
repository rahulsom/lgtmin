package domain.letsencrypt

import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Unindexed

/**
 * Represents a request/response pair that's preloaded for lets encrypt to work.
 *
 * @author Rahul Somasunderam
 */
@Entity(unindexed = false)
@ToString
class Challenge {
    String challengeText
    @Unindexed String responseText
}