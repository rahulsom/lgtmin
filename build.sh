#!/bin/sh
set -e

if [ "$TRAVIS_PULL_REQUEST" = false ]; then
  ./gradlew -q test && ./gradlew -q gaeUpload
else
  ./gradlew -q test
fi