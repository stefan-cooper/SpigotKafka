package com.stefancooper.KafkaMinecraft;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.UUID;

public class Produce {

    private Properties props = new Properties();
    private KafkaProducer<String, String> producer;

    public Produce () {
        Thread.currentThread().setContextClassLoader(null);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "Producer");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<>(props);
    }

    public void produceMessage(String topic, String produceContent) {
        this.producer.send(new ProducerRecord<>(topic, UUID.randomUUID().toString(), produceContent));
    }
}
