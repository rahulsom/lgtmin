#!/bin/sh
set -e

cat > src/main/resources/oauth.properties << EOF
clientId=${LGTMIN_CLIENT_ID}
clientSecret=${LGTM_SECRET}
EOF

if [ "$TRAVIS_PULL_REQUEST" = false ]; then
  if [ "$TRAVIS_BRANCH" = "master" ]; then
    # gradle clean check && gradle appengineUpdateAll 
    ./gradlew clean check && cat ./build/exploded-app/WEB-INF/appengine-generated/datastore-indexes-auto.xml
  else
    gradle clean check
  fi
else
  gradle clean check
fi
