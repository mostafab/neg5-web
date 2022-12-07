#!/bin/bash

set -e

debug_port=1044

cd $(dirname "$0")
cd ../../
mvn package --file pom.xml -DskipTests
cd service/
java -classpath %classpath -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=$debug_port -jar ./target/neg5.service-1.0-SNAPSHOT-jar-with-dependencies.jar
