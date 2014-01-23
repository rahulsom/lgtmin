#!/bin/sh
set -e

if [ "$TRAVIS_PULL_REQUEST" = false ]; then
  gradle -q clean test && gradle -q gaeUpdateAll
else
  gradle -q clean test
fi
