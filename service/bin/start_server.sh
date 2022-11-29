#!/bin/bash

set -e

cd $(dirname "$0")
cd ..
mvn exec:exec -Dexec.executable="java" -Dexec.args="-classpath %classpath -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044 org.neg5.Main"