#!/bin/bash -xe

profile=$1

docker rm -f -v $profile-restbucks-ordering-db || true

docker run \
            --name $profile-restbucks-ordering-db \
            -e MYSQL_ALLOW_EMPTY_PASSWORD=yes \
            -e MYSQL_DATABASE=restbucks_ordering \
            -e MYSQL_USER=ordering \
            -e MYSQL_PASSWORD=123456 \
            -P \
            -d \
            mysql:5.6

