#!/usr/bin/env bash

profile=$1

docker run --name $profile-restbucks-ordering \
           -p 8080 \
           -d \
           --link $profile-restbucks-ordering-db:db \
           hippoom/restbucks \
           java -jar build/libs/restbucks-ordering-0.1-dev.jar