package org.example.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.dto.KafkaProperty;
import org.example.dto.SensorData;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;

import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

public class MyProducer implements Runnable {
    private final BlockingQueue<SensorData> queue;
    private final KafkaProperty properties;

    public MyProducer(BlockingQueue queue, KafkaProperty properties) {
        this.queue = queue;
        this.properties = properties;
    }

    @Override
    public void run() {
        final Properties props = new Properties();
        System.out.println("Producer Start : " + Thread.currentThread().getName());

        // User-specific properties that you must set
        props.put(BOOTSTRAP_SERVERS_CONFIG, properties.produceServerId());

        // Fixed properties
        props.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getCanonicalName());
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getCanonicalName());
        props.put(ACKS_CONFIG, "all");

        try (final Producer<String, String> producer = new KafkaProducer<>(props)) {
            while (true) {
                processQueue(producer);
                Thread.sleep(100);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processQueue(Producer<String, String> producer) throws JsonProcessingException {
        while (!queue.isEmpty()) {
            if (queue.peek() instanceof SensorData) {
                ObjectMapper objectMapper = new ObjectMapper();
                String value = objectMapper.writeValueAsString(queue.poll());
                System.out.println("send message  : " + value);
                sendMessage(producer, "sensor-data", value);
            } else {
                System.out.println("queue = " + queue.poll());
            }
        }
    }

    private static void sendMessage(Producer<String, String> producer, String key, String value) {
        producer.send(
                new ProducerRecord<>("test-topic", key, value),
                (event, ex) -> {
                    if (ex != null)
                        ex.printStackTrace();
                    else
                        System.out.println("Produced event to topic : " + event.topic());
                });
    }
}
