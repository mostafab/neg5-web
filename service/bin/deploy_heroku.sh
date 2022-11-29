#!/bin/bash

set -e

cd $(dirname "$0")
cd ../..
mvn clean
mvn install
cd service/
mvn heroku:deploy