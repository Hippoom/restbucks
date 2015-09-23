#!/usr/bin/env bash

user_home="$(eval echo ~$USER)"

profile=$1

docker run --rm \
           -t \
           -v $(pwd):/project \
           -v $user_home/.gradle:/root/.gradle \
           -w /project \
           --link $profile-restbucks-ordering-db:db \
           hippoom/restbucks \
           ./gradlew flywayMigrate -i \
           -Dflyway.user=ordering \
           -Dflyway.password=123456 \
           -Dflyway.url=jdbc:mysql://db:3306/restbucks_ordering?useUnicode=true&characterEncoding=utf-8