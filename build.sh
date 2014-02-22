#!/bin/sh
set -e

mkdir src/main/resources/ || echo "Resources Dir exists"

cat > src/main/resources/oauth.properties << EOF
clientId=${LGTMIN_CLIENT_ID}
clientSecret=${LGTM_SECRET}
EOF

if [ "$TRAVIS_PULL_REQUEST" = false ]; then
  gradle -q check && gradle -q gaeUpdateAll
else
  gradle -q check
fi
