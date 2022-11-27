#!/bin/bash

cd $(dirname "$0")

set -e

source ./db.env

./start_local_db.sh

echo "Waiting for postgres container to be ready..."
sleep 5

echo "Creating database..."
psql -h ${HOST} -p ${PORT} -U postgres -c "create database ${DB};" >/dev/null

echo "Creating application user..."
psql -h ${HOST} -p ${PORT} -U postgres -c "create user \"${USER_APP}\" WITH LOGIN;" >/dev/null
psql -h ${HOST} -p ${PORT} -U postgres -c "grant create on database ${DB} to \"${USER_APP}\";" >/dev/null

cd ../
mvn flyway:migrate
