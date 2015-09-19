#!/usr/bin/env bash

profile=$1

docker stop $profile-restbucks-ordering

docker rm $profile-restbucks-ordering

docker run --name $profile-restbucks-ordering \
           -p 8080 \
           -d \
           hippoom/restbucks \
           java -jar build/libs/restbucks-ordering-0.1-dev.jar