# KafkaMinecraft-Events

Expose events on a Minecraft server via Kafka so that they can be consumed for third party apps (e.g. mobile push notifications, discord bots, etc.)

## What is this?

This repo contains some experimentation around exposing (producing) the Spigot API via kafka topics and experimenting with how they can be used in third party apps.

## Getting started

## Running

1. Clone the repo
2. Run kafka

  ```
  ./kafka/run.sh up
  ```

3. Run Spigot server

  ```
  ./run_server.sh
  ```

4. Run any 3rd party consumers

### TODO

- write some more 3rd party apps (discord bot!)
- expose more minecraft api endpoints to kafka