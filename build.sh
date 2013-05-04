#!/bin/sh
set -e

if [ "$TRAVIS_PULL_REQUEST" = false ]; then
  gradle -q clean test && gradle gaeUpload || cat build/test-results/*.xml
else
  gradle -q clean test || cat build/test-results/*.xml
fi