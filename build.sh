#!/usr/bin/env bash

user_home="$(eval echo ~$USER)"

docker run --rm \
           -t \
           -v $(pwd):/project \
           -v $user_home/.gradle:/root/.gradle \
           -w /project \
           java:8 \
           ./gradlew clean build
