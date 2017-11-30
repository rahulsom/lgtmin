package services

import domain.Image
import domain.UserList
import groovy.util.logging.Log
import io.reactivex.Maybe
import util.AppUtil

import java.security.SecureRandom

import static com.google.appengine.api.memcache.Expiration.byDeltaMillis
import static util.AppUtil.*

/**
 * Created by rahulsomasunderam on 8/9/14.
 */
@Singleton
@SuppressWarnings("GrMethodMayBeStatic")
@Log
class LgtmService {

    @Delegate private AppUtil appUtil = AppUtil.instance

    Maybe<Integer> getCount() {
        getCachedValue(COUNT) {
            Image.countNotDeleted()
        }
    }

    Maybe<List<Image>> getImageList() {
        getCount().flatMap { ct ->
            getCachedValue(ALL_IMAGES) {
                Image.listSortedByCredits(ct)
            }
        }
    }

    Maybe<List<Image>> getTopImages() {
        getCachedValue(TOP_IMAGES) {
            Image.listSortedByCredits(12)
        }
    }

    Maybe<Image> getImage(String hash) {
        getCachedValue("/i/${hash}".toString(), byDeltaMillis(DAY)) {
            Image.findByHash(hash)
        }
    }

    Maybe<UserList> getUserList(String userName) {
        log.info "getUserList('$userName')"
        imageList.flatMap { il ->
            getCachedValue("/l/${userName}".toString(), byDeltaMillis(DAY)) {
                UserList myList = UserList.findByUsername(userName)
                if (myList) {
                    if (!myList.hashes) {
                        myList.hashes = []
                    }
                    myList.hashes = il.findAll { myList.hashes.contains(it.hash) }*.hash
                    myList.save()
                } else {
                    myList = new UserList(username: userName, hashes: [])
                    myList.save()
                }

                log.info "getUserList('$userName') -> $myList"
                myList
            }
        }
    }

    def <T> T getRandom(List<T> list, int ct) {
        def random = Math.abs(new SecureRandom().nextGaussian() / 2.0)
        log.info "Random: ${random}"
        def theOffset = Math.floor(random * ct).intValue()
        if (theOffset >= ct) {
            theOffset = theOffset % ct
        }

        log.info "Fetching ${theOffset} of ${ct}"

        return list[theOffset]
    }
}
