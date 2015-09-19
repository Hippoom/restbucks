#!/usr/bin/env bash

user_home="$(eval echo ~$USER)"

profile=$1

docker run --name $profile-restbucks-ordering-smoke \
           --rm \
           -t \
           -v $(pwd):/project \
           -v $user_home/.gradle:/root/.gradle \
           -w /project \
           --link $profile-restbucks-ordering:app \
           java:8 \
           ./gradlew runSmokeTest
