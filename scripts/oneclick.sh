#!/usr/bin/env bash -e


profile=$1

script_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. "$script_dir"/common.sh #use quote here to compliant with space in dir

rm -rf $project_home/build

. "$script_dir"/build.sh

. "$script_dir"/dbsetup.sh $profile
sleep 10

. "$script_dir"/deploy.sh $profile

sleep 10
. "$script_dir"/smoke.sh $profile
. "$script_dir"/feature.sh $profile