#!/usr/bin/env bash -e

profile=$1

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh

docker rm -f -v $profile-restbucks-ordering || true

docker run --name $profile-restbucks-ordering \
           -p 8080 \
           -d \
           --link $profile-restbucks-ordering-db:db \
           -v $project_home:/project \
           -w /project \
           java:8  \
           java -jar build/libs/restbucks-ordering.jar