#!/bin/sh
set -e

if [ "$TRAVIS_PULL_REQUEST" = false ]; then
  gradle clean test && gradle gaeUpload
else
  gradle clean test
fi