#!/usr/bin/env bash -e

profile=$1

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh


docker rm -f -v $profile-restbucks-ordering-db || true

docker run \
            --name $profile-restbucks-ordering-db \
            -e MYSQL_ALLOW_EMPTY_PASSWORD=yes \
            -P \
            -d \
            mysql:5.6

