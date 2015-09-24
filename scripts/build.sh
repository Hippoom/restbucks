#!/usr/bin/env bash

WORKSPACE="$( cd "$( dirname "${BASH_SOURCE[0]}" )/../" && pwd )"

user_home="$(eval echo ~$USER)"

docker run --rm \
           -t \
           -v $user_home/.gradle:/root/.gradle \
           -v $WORKSPACE:/project \
           -w /project \
           java:8 \
           ./gradlew clean build
