#!/bin/sh
set -e

cat > src/main/resources/oauth.properties << EOF
clientId=${LGTMIN_CLIENT_ID}
clientSecret=${LGTM_SECRET}
EOF

if [ "$TRAVIS_PULL_REQUEST" = false ]; then
  if [ "$TRAVIS_BRANCH" = "master" ]; then
    # gradle clean check && gradle appengineUpdateAll
    mv .appcfg_oauth2_tokens_java $HOME/
    md5sum ~/.appcfg_oauth2_tokens_java

    ./gradlew clean test && ./gradlew appengineUpdateAll
  else
    ./gradlew clean check
  fi
else
  ./gradlew clean check
fi
