import java.security.SecureRandom

/**
 * Created by rahulsomasunderam on 2/26/14.
 */
response.status = 404
def images = [
        'http://static2.businessinsider.com/image/50256389ecad047d37000004-900-675/mckayla-maroney-pissed.jpg',
        'http://cdn01.cdn.justjared.com/wp-content/uploads/headlines/2012/11/president-obama-mckayla-maroney-not-impressed-meeting.jpg',
        'http://www.zwani.com/graphics/sorry/images/344.gif',
]
def random = new SecureRandom().nextInt(images.size())
request.setAttribute 'image', images[random]
forward '/WEB-INF/pages/error.gtpl'