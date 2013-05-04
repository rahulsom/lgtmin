#!/bin/sh
set -e

if [ "$TRAVIS_PULL_REQUEST" = false ]; then
  ./gradlew -q clean test && gradle gaeUpload || cat build/test-results/*.xml
else
  ./gradlew -q clean test || cat build/test-results/*.xml
fi