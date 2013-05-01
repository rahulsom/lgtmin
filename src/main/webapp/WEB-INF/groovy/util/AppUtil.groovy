package util

import groovyx.gaelyk.GaelykBindings

/**
 * Helps extract the application's root url
 * @author rahulsomasunderam
 */
@GaelykBindings
@Singleton
class AppUtil {
  String getRoot() {
    app.env.name.toString() == 'Development' ? 'http://localhost:8080' : 'http://www.lgtm.in'
  }
}
