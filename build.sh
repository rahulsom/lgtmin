#!/bin/sh
set -e

cat > src/main/resources/oauth.properties << EOF
clientId=${LGTMIN_CLIENT_ID}
clientSecret=${LGTM_SECRET}
EOF

cat > src/main/resources/jwt.properties << EOF
key=${JWT_SIGNING_KEY}
EOF

if [ "$TRAVIS_EVENT_TYPE" = "cron" ]; then
  echo "Cron mode"
  if [ ! -d "$HOME/google-cloud-sdk/bin" ]; then
    rm -rf $HOME/google-cloud-sdk
    curl https://sdk.cloud.google.com | bash
  fi
  export PATH=$PATH:$HOME/google-cloud-sdk/bin

  export CLOUDSDK_CORE_PROJECT=lgtmin

  groovy letsencrypt.groovy
  cat lgtm.in.crt lgtm.in.chain.crt > lgtm.in.combined.crt

  export CERTNAME=lgtmin_$(date +"%Y%m%d")
  
  gcloud beta app ssl-certificates create \
        --display-name $CERTNAME \
        --certificate lgtm.in.combined.crt \
        --private-key lgtm.in.key

  CERT_ID=$(gcloud beta app ssl-certificates list | grep $CERTNAME | tr -s " " | cut -d " " -f 1 | head -1)
  echo $CERT_ID

  gcloud beta app domain-mappings update lgtm.in --certificate-id $CERT_ID
  gcloud beta app domain-mappings update www.lgtm.in --certificate-id $CERT_ID

  rm -f rm *.key *.csr *.crt
else
  if [ "$TRAVIS_PULL_REQUEST" = false ]; then
    if [ "$TRAVIS_BRANCH" = "master" ]; then
      cat .appcfg_oauth2_tokens_java | sed -e "s/rahul/$(whoami)/g" > $HOME/.appcfg_oauth2_tokens_java
      ./gradlew check
      ./gradlew appengineUpdateAll
    else
      ./gradlew check
    fi
  else
    ./gradlew check
  fi
fi
