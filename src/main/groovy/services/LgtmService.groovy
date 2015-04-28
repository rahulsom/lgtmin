package services

import domain.Image
import domain.UserList
import util.AppUtil

import static util.AppUtil.TOP_IMAGES
import com.google.appengine.api.memcache.Expiration

/**
 * Created by rahulsomasunderam on 8/9/14.
 */
@Singleton
@SuppressWarnings("GrMethodMayBeStatic")
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
        AppUtil.instance.getCachedValue("/i/${hash}", Expiration.byDeltaMillis(AppUtil.DAY)) {
            Image.findByHash(hash)
        }
    }

    UserList getUserList(String userName) {
        AppUtil.instance.getCachedValue("/l/${userName}", Expiration.byDeltaMillis(AppUtil.DAY)) {
            UserList myList = UserList.findByUsername(userName)
            if (!myList) {
                myList = new UserList(username: userName, hashes: [])
                myList.save()
            }

            if (!myList.hashes) {
                myList.hashes = [];
            }

            myList
        }
    }
}
