#!/usr/bin/env bash

WORKSPACE="$( cd "$( dirname "${BASH_SOURCE[0]}" )/../" && pwd )"
user_home="$(eval echo ~$USER)"
version=$(eval cat $WORKSPACE/build/version)

profile=$1

docker run --rm \
           -t \
           -v $user_home/.gradle:/root/.gradle \
           -v $WORKSPACE:/project \
           -w /project \
           --link $profile-restbucks-ordering-db:db \
           hippoom/restbucks-ordering:$version \
           ./gradlew flywayMigrate -i\
           -Dflyway.user=ordering \
           -Dflyway.password=123456 \
           -Dflyway.url="jdbc:mysql://db:3306/restbucks_ordering?useUnicode=true&characterEncoding=utf-8"