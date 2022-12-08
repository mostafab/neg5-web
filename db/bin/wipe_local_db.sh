#!/bin/bash

cd $(dirname "$0")
source ./db.env
./stop_local_db.sh
docker ps -a | grep $CONTAINER_NAME | awk '{print $1}' | xargs docker rm -f

docker volume rm "${COMPOSE_PROJECT_NAME}_${VOLUME_NAME}"
