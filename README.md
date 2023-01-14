# KafkaMinecraft-Events

Expose events on a Minecraft server via Kafka so that they can be consumed for third party apps (e.g. mobile push notifications, discord bots, etc.)

## What is this?

This repo contains some experimentation around exposing (producing) the Spigot API via kafka topics and experimenting with how they can be used in third party apps.

## Getting started

## Running

1. Clone the repo
2. Run docker-compose to run kafka/zookeeper (TODO - currently you need to download and run seperately)
3. `mvn -DPLUGIN_LOCATION="<path/to/spigot/server/plugins" clean install`
4. Run Spigot server
5. Run any 3rd party consumers

### TODO

- write dockerfile/docker-compose to more easily set up a kafka to use (download and run)
- write dockerfile/docker-compose to download and run new spigot server with fresh plugins
- write some more 3rd party apps (discord bot!)
- expose more minecraft api endpoints to kafka