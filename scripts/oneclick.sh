#!/usr/bin/env bash -e


profile=$1

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"


. $DIR/build.sh

. $DIR/dbsetup.sh $profile
sleep 10
. $DIR/dbmigrate.sh $profile
. $DIR/deploy.sh $profile

sleep 10
. $DIR/smoke.sh $profile
. $DIR/feature.sh $profile