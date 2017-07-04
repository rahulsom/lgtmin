#!/bin/bash
if [ ! -f /tmp/logs.txt ]; then
    APPENGINE_DIR=$(ls -1 /Users/rahul/.gradle/appengine-sdk | tail -1)
    chmod u+x ~/.gradle/appengine-sdk/$APPENGINE_DIR/bin/appcfg.sh
    chmod u+x ~/.gradle/appengine-sdk/$APPENGINE_DIR/bin/run_java.sh
    ~/.gradle/appengine-sdk/$APPENGINE_DIR/bin/appcfg.sh request_logs build/exploded-app /tmp/logs.txt
fi
cat /tmp/logs.txt \
    | sed -e "s/.*GET//g" | sed -e "s/.*POST//g" \
    | cut -d " " -f 2 \
    | sort \
    | sed -e "s/\/u\/.*/\/u\/hash/g" \
    | sed -e "s/\/r\/.*/\/r\/hash/g" \
    | sed -e "s/\/i\/.*/\/i\/hash/g" \
    | sed -e "s/\/p\/.*/\/p\/hash/g" \
    | sed -e "s/\/m\/.*/\/m\/hash/g" \
    | sed -e "s/\/g\/.*/\/g\/user/g" \
    | sed -e "s/\/g\?.*/\/g\?random/g" \
    | sed -e "s/\/auth\/me.*/\/auth\/me/g" \
    | sed -e "s/\/l\/.*/\/l\/user/g" \
    | uniq -c
