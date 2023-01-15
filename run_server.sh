#!/bin/bash

. setup_server.sh

cd server && java -jar spigot-"${MINECRAFT_VERSION}".jar -nogui
