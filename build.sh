#!/bin/sh
set -e

if [ "$TRAVIS_PULL_REQUEST" = false ]; then
  gradle -q -S test && gradle -q -S gaeUpload
else
  gradle -q -S test
fi