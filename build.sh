#!/bin/sh
set -e

if [ "$TRAVIS_PULL_REQUEST" = false ]; then
  gradle test && gradle gaeUpload
else
  gradle test
fi