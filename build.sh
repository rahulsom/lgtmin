#!/bin/sh
set -e

if [ "$TRAVIS_PULL_REQUEST" = false ]; then
  gradle -q clean test && gradle gaeUpload
else
  gradle -q clean test
fi