#!/usr/bin/env bash

profile=$1

docker run --name $profile-restbucks-ordering \
           -p 8080 \
           -d \
           -v $(pwd):/project \
           -w /project \
           java:8 \
           java -jar build/libs/restbucks-ordering-0.1-dev.jar