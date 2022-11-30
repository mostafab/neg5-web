#!/bin/bash

set -e

cd $(dirname "$0")
cd ../..
mvn clean
mvn install
# Get confirmation of deploy
#read -p "Proceed with deploy to Heroku? (Y/N): " confirm && [[ $confirm == [yY] || $confirm == [yY][eE][sS] ]] || exit 1
cd service/
#mvn heroku:deploy
cd bin/
./create_new_tag.sh -v major