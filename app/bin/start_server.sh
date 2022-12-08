#!/bin/bash

set -e

debug_port=1044

cd $(dirname "$0")
cd ../../
mvn package --file pom.xml -DskipTests
cd app/
java -classpath %classpath -Xmx256m -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=$debug_port -jar ./target/app-1.0-SNAPSHOT-jar-with-dependencies.jar
