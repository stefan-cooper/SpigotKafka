const { Router } = require("express");
const { Kafka } = require('kafkajs')
const router = Router();

const kafka = new Kafka({
  clientId: 'my-app',
  brokers: ['localhost:9092']
})

const consumer = kafka.consumer({ groupId: Math.random().toString() })
let notifs = []
consumer.connect()
consumer.subscribe({ topic: 'motion' })
consumer.run({ eachMessage: async ({ topic, partition, message }) => { notifs.push(message.value.toString()); return true } })

router.get("/kafka_poll", (req, res) => {
  res.json(notifs)
  notifs = []
});

module.exports = router;
