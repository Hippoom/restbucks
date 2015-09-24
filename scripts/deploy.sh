#!/usr/bin/env bash

WORKSPACE="$( cd "$( dirname "${BASH_SOURCE[0]}" )/../" && pwd )"
version=$(eval cat $WORKSPACE/build/version)

profile=$1

docker run --name $profile-restbucks-ordering \
           -p 8080 \
           -d \
           --link $profile-restbucks-ordering-db:db \
           hippoom/restbucks-ordering:$version \
           java -jar build/libs/restbucks-ordering.jar