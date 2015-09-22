#!/usr/bin/env bash

user_home="$(eval echo ~$USER)"

profile=$1

docker run --rm \
           -t \
           -v $user_home/.gradle:/root/.gradle \
           hippoom/restbucks \
           ./gradlew flywayMigrate -i \
           -Dflyway.url=jdbc:mysql://localhost:3306/restbucks_ordering?useUnicode=true&characterEncoding=utf-8 \
           -Dflyway.user=ordering \
           -Dflyway.password=123456