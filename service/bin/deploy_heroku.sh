#!/bin/bash

set -e

cd $(dirname "$0")
cd ../..
mvn clean install -DskipITs
# Get confirmation of deploy
read -p "Proceed with deploy to Heroku? (Y/N): " confirm && [[ $confirm == [yY] || $confirm == [yY][eE][sS] ]] || exit 1
cd service/
mvn clean heroku:deploy

# Create a new Git tag to capture changes
cd bin/
echo "Deploy to Heroku succeeded. Creating a new Git tag:"
./create_new_tag.sh -v major