#!/bin/bash

cd $(dirname "$0")
docker-compose --env-file ./db.env up -d