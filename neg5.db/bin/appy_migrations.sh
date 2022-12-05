#!/bin/bash

cd $(dirname "$0")

set -e

cd ..
mvn compile flyway:migrate