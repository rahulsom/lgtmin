#!/bin/sh
set -e

mv .appcfg_oauth2_tokens_java $HOME/

cat > src/main/resources/oauth.properties << EOF
clientId=${LGTMIN_CLIENT_ID}
clientSecret=${LGTM_SECRET}
EOF

if [ "$TRAVIS_PULL_REQUEST" = false ]; then
  if [ "$TRAVIS_BRANCH" = "master" ]; then
    # gradle clean check && gradle appengineUpdateAll
    ./gradlew clean test && ./gradlew appengineUpdateAll
  else
    ./gradlew clean check
  fi
else
  ./gradlew clean check
fi
