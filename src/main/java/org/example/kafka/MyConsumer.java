package org.example.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.dto.KafkaProperty;
import org.example.dto.SensorData;
import org.example.parser.Parser;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

public class MyConsumer implements Runnable {
    private final BlockingQueue<SensorData> queue;
    private final KafkaProperty properties;

    public MyConsumer(BlockingQueue queue, KafkaProperty properties) {
        this.queue = queue;
        this.properties = properties;
    }

    @Override
    public void run() {
        Properties props = new Properties();
        // User-specific properties that you must set
        props.put(BOOTSTRAP_SERVERS_CONFIG, properties.consumeServerId());
        // Fixed properties
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        props.put(GROUP_ID_CONFIG, properties.consumeGroupId());

        try (final Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
            String topic = properties.topic();

            consumer.subscribe(Arrays.asList(topic));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                if (records == null) {
                    return;
                }

                for (ConsumerRecord<String, String> record : records) {
                    String readTopic = record.topic();
                    if (topic.equals(readTopic)) {
                        String value = record.value();
                        queue.add(Parser.parse(value));

                        System.out.println(
                                String.format("Consumed event from topic %s: value = %s", topic, value));
                    }
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
