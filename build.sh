#!/bin/sh
set -e

cat > src/main/resources/oauth.properties << EOF
clientId=${LGTMIN_CLIENT_ID}
clientSecret=${LGTM_SECRET}
EOF

if [ "$TRAVIS_PULL_REQUEST" = false ]; then
  if [ "$TRAVIS_BRANCH" = "master" ]; then
    gradle -d clean check && gradle -d appengineUpdateAll 
  else
    gradle clean check
  fi
else
  gradle clean check
fi
