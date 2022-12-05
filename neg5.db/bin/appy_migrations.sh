#!/bin/bash

cd $(dirname "$0")

set -e

cd ../..
mvn compile

cd neg5.db/
mvn flyway:migrate