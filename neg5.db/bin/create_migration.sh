#!/bin/bash

cd $(dirname "$0")

set -e

template=$'CREATE TABLE IF NOT EXISTS <table> (
  id bigserial
);
'

migration_name=$1

if [ -z "${migration_name}" ];
  then echo "No migration name given"
  exit 1
fi

epoch_time=$(date +%s)
filename="V${epoch_time}__${migration_name}.sql"

cd ../src/main/resources/migrations
touch $filename
chmod a+r $filename

echo "$template" > $filename

git add $filename

echo "Created ${PWD}/${filename}"

