#!/usr/bin/env bash -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh

docker run --rm \
           -t \
           -v $user_home/.gradle:/root/.gradle \
           -v $WORKSPACE:/project \
           -w /project \
           java:8 \
           ./gradlew clean build
