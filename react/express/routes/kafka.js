const { Router } = require("express");
const { Kafka } = require('kafkajs')
const router = Router();

const kafka = new Kafka({
  clientId: 'my-app',
  brokers: ['localhost:9092']
})

const consumer = kafka.consumer({ groupId: Math.random().toString() })
let motionNotifs = []
let loginNotifs = []
let ringNotifs = []
consumer.connect()
consumer.subscribe({ topic: 'motion' })
consumer.subscribe({ topic: 'login' })
consumer.subscribe({ topic: 'ring' })
consumer.run({ eachMessage: async ({ topic, partition, message }) => { 
  console.log(topic)
  if (topic === 'motion') motionNotifs.push(message.value.toString()); 
  else if (topic === 'login') loginNotifs.push(message.value.toString());
  else if (topic === 'ring') ringNotifs.push(message.value.toString());
  return true } 
})

router.get("/kafka_motion", (req, res) => {
  res.json(motionNotifs)
  motionNotifs = []
});

router.get("/kafka_login", (req, res) => {
  res.json(loginNotifs)
  loginNotifs = []
});

router.get("/kafka_ring", (req, res) => {
  res.json(ringNotifs)
  ringNotifs = []
});

module.exports = router;
