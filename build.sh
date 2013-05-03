#!/bin/sh
set -e

if [ "$TRAVIS_PULL_REQUEST" = false ]; then
  gradle -q test && gradle -q gaeUpload
else
  gradle -q test
fi