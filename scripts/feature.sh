#!/usr/bin/env bash -e


profile=$1

script_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. "$script_dir"/common.sh #use quote here to compliant with space in dir

docker run --rm \
           -t \
           -v $user_home/.gradle:/root/.gradle \
           --link $profile-restbucks-ordering:app \
           -v $project_home:/project \
           -w /project \
           java:8 \
           ./gradlew featureTest
