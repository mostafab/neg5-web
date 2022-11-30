#!/bin/bash

set -e

while getopts r: flag
do
  case "${flag}" in
    r) release=${OPTARG};;
  esac
done

cd $(dirname "$0")
cd ../..
mvn clean
mvn install
# Get confirmation of deploy
read -p "Proceed with deploy to Heroku? (Y/N): " confirm && [[ $confirm == [yY] || $confirm == [yY][eE][sS] ]] || exit 1
cd service/
mvn heroku:deploy

# Create a new Git tag to capture changes
cd bin/
echo "Deploy to Heroku succeeded. Creating a new Git tag:"
./create_new_tag.sh -v $release