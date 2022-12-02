#!/bin/bash

set -e

debug_port=1044
entrypoint="neg5.service.Main"

cd $(dirname "$0")
cd ../../
mvn compile
cd service/
mvn exec:exec -Dexec.executable="java" -Dexec.args="-classpath %classpath -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=$debug_port $entrypoint"