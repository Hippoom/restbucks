#!/usr/bin/env bash -e

profile=$1

script_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. "$script_dir"/common.sh #use quote here to compliant with space in dir

docker rm -f -v $profile-restbucks-ordering || true

docker run --name $profile-restbucks-ordering \
           -p 8080 \
           -d \
           --link $profile-restbucks-ordering-db:db \
           -v $project_home:/project \
           -w /project \
           java:8  \
           java -jar build/libs/restbucks-ordering.jar