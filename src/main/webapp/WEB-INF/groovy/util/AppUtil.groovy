package util

import groovyx.gaelyk.GaelykBindings

/**
 * Created with IntelliJ IDEA.
 * User: rahulsomasunderam
 * Date: 4/30/13
 * Time: 8:31 PM
 * To change this template use File | Settings | File Templates.
 */
@GaelykBindings
@Singleton
class AppUtil {
  String getRoot() {
    app.env.name.toString() == 'Development' ? 'http://localhost:8080' : 'http://www.lgtm.in'
  }
}
