#!/usr/bin/env bash

profile=$1
user_home="$(eval echo ~$USER)"

docker run \
            --name $profile-restbucks-ordering-db \
            -e MYSQL_ALLOW_EMPTY_PASSWORD=yes \
            -P \
            -d \
            mysql:5.6

