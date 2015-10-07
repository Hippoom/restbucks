#!/usr/bin/env bash -e

profile=$1

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh


docker run \
            --rm \
            -t \
            -v $WORKSPACE:/project \
            -w /project \
            --link $profile-restbucks-ordering-db:db \
            mysql \
            sh -c 'exec mysql -h"$DB_PORT_3306_TCP_ADDR" -P"$DB_PORT_3306_TCP_PORT" -uroot < src/main/resources/db/init.sql'
