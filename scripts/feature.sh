#!/usr/bin/env bash -e


profile=$1

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh


docker run --rm \
           -t \
           -v $user_home/.gradle:/root/.gradle \
           --link $profile-restbucks-ordering:app \
           -v $project_home:/project \
           -w /project \
           java:8 \
           ./gradlew featureTest
