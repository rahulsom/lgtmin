package services

import com.google.appengine.api.memcache.Expiration
import domain.Image
import domain.UserList
import groovy.util.logging.Log
import util.AppUtil

import java.security.SecureRandom

import static util.AppUtil.TOP_IMAGES

/**
 * Created by rahulsomasunderam on 8/9/14.
 */
@Singleton
@SuppressWarnings("GrMethodMayBeStatic")
@Log
class LgtmService {
    int getCount() {
        AppUtil.instance.getCachedValue(AppUtil.COUNT) {
            Image.countNotDeleted()
        }
    }

    List<Image> getImageList() {
        AppUtil.instance.getCachedValue(AppUtil.ALL_IMAGES) {
            Image.listSortedByCredits(getCount())
        }
    }

    List<Image> getTopImages() {
        AppUtil.instance.getCachedValue(TOP_IMAGES) {
            Image.listSortedByCredits(12)
        }
    }

    Image getImage(String hash) {
        AppUtil.instance.getCachedValue("/i/${hash}".toString(), Expiration.byDeltaMillis(AppUtil.DAY)) {
            Image.findByHash(hash)
        }
    }

    UserList getUserList(String userName) {
        log.info "getUserList('$userName')"
        AppUtil.instance.getCachedValue("/l/${userName}".toString(), Expiration.byDeltaMillis(AppUtil.DAY)) {
            UserList myList = UserList.findByUsername(userName)
            if (!myList) {
                myList = new UserList(username: userName, hashes: [])
                myList.save()
            }

            if (!myList.hashes) {
                myList.hashes = []
            }

            log.info "getUserList('$userName') -> $myList"
            myList
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
