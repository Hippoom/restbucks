#!/usr/bin/env bash

script_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. "$script_dir"/common.sh #use quote here to compliant with space in dir

version=$(cat "$project_home"/build/version)

docker build -t "hippoom/restbucks-ordering-test:$version" -f "$project_home"/Dockerfile.test "$project_home"
