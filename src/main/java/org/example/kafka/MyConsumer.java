package org.example.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.parser.Parser;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

public class MyConsumer implements Runnable {
    private final Queue queue;
    private final String consumeServerIp;
    private final String produceServerIp;
    private final String topic;
    private final ExecutorService executorService;

    public MyConsumer(Queue queue, String consumeServerIp, String produceServerIp, String topic) {
        this.queue = queue;
        this.consumeServerIp = consumeServerIp;
        this.produceServerIp = produceServerIp;
        this.topic = topic;
        this.executorService = Executors.newFixedThreadPool(3);
    }

    @Override
    public void run() {
        Properties props = new Properties();
        // User-specific properties that you must set
        props.put(BOOTSTRAP_SERVERS_CONFIG, consumeServerIp);
        // Fixed properties
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        props.put(GROUP_ID_CONFIG, "consumer-group");

        try (final Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Arrays.asList(topic));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String key = record.key();
                    String value = record.value();
                    executorService.submit(new Parser(value, queue));

                    System.out.println(
                            String.format("Consumed event from topic %s: key = %-10s value = %s", topic, key, value));
                }
                if (!queue.isEmpty()) {
                    executorService.submit(new MyProducer(queue, produceServerIp));
                }
            }
        } finally {
            shutdownExecutorService();
        }
    }

    private void shutdownExecutorService() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
