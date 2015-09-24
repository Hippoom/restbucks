#!/usr/bin/env bash

WORKSPACE="$( cd "$( dirname "${BASH_SOURCE[0]}" )/../" && pwd )"
user_home="$(eval echo ~$USER)"

profile=$1

version=$(eval cat $WORKSPACE/build/version)

docker run --rm \
           -t \
           -v $user_home/.gradle:/root/.gradle \
           --link $profile-restbucks-ordering:app \
           hippoom/restbucks-ordering:$version \
           ./gradlew smokeTest
