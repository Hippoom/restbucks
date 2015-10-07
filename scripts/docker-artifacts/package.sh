#!/usr/bin/env bash

WORKSPACE="$( cd "$( dirname "${BASH_SOURCE[0]}" )/../" && pwd )"

version=$(eval cat $WORKSPACE/build/version)

docker build -t "hippoom/restbucks-ordering:$version" $WORKSPACE
