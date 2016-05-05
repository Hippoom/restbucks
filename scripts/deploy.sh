#!/bin/bash -xe

script_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. "$script_dir"/common.sh #use quote here to compliant with space in dir

profile=$1
version=$2

docker rm -f -v $profile-restbucks-ordering || true

docker run --name $profile-restbucks-ordering \
           -P \
           -d \
           --link $profile-restbucks-ordering-db:db \
           hippoom/restbucks-ordering:$version