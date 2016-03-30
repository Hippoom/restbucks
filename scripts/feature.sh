#!/usr/bin/env bash -e

script_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. "$script_dir"/common.sh #use quote here to compliant with space in dir

profile=$1
version=$2


docker run --rm \
           -t \
           -v $user_home/.gradle:/root/.gradle \
           --link $profile-restbucks-ordering:app \
           hippoom/restbucks-ordering-test:$version \
           featureTest
