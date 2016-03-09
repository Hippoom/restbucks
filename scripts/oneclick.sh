#!/usr/bin/env bash -e


profile=$1

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh

rm -rf $project_home/build

. $DIR/build.sh

. $DIR/dbsetup.sh $profile
sleep 10

. $DIR/deploy.sh $profile

sleep 10
. $DIR/smoke.sh $profile
. $DIR/feature.sh $profile