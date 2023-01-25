#!/bin/sh

set -ex

REFRESH_BUILD=${REFRESH_BUILD:-false}
MINECRAFT_VERSION=${MINECRAFT_VERSION:-1.19.3}
export MINECRAFT_VERSION=${MINECRAFT_VERSION}

./build_plugin.sh

mkdir -p server
mkdir -p server/plugins
cp build/KafkaMinecraft-0.0.1.jar server/plugins/KafkaMinecraft-0.0.1.jar

if [ "${REFRESH_BUILD}" = "true" ]; then
  cd server
  curl -o BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
  java -jar BuildTools.jar --rev "${MINECRAFT_VERSION}"
  cd ..
fi

