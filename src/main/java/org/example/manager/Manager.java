package org.example.manager;

import org.example.dto.KafkaProperty;
import org.example.kafka.MyConsumer;
import org.example.kafka.MyProducer;
import org.example.reader.PropertyReader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Manager implements Runnable {
    private BlockingQueue queue;
    private static KafkaProperty PROPERTIES = new KafkaProperty();
    private static final String CONSUME_SERVER_ID = "consume.server.id";
    private static final String PRODUCE_SERVER_ID = "produce.server.id";
    private static final String CONSUME_GROUP_ID = "consume.group.id";
    private static final String PRODUCE_GROUP_ID = "produce.group.id";

    @Override
    public void run() {
        System.out.println("[Main: Start] queue size : " + queue.size());
        initQueue();
        loadProperties();

        Thread consumer = new Thread(new MyConsumer(queue, PROPERTIES), "consumer");
        Thread producer = new Thread(new MyProducer(queue, PROPERTIES), "producer");

        try {
            consumer.start();
            consumer.join();
            producer.start();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void initQueue() {
        if (this.queue == null) {
            this.queue = new LinkedBlockingQueue();
        }
    }

    private boolean loadProperties() {
        PropertyReader propertyReader = new PropertyReader();

        try {
            String consumeServerId = propertyReader.getStringKey(CONSUME_SERVER_ID);
            PROPERTIES.consumeServerId(consumeServerId);

            String produceServerId = propertyReader.getStringKey(PRODUCE_SERVER_ID);
            PROPERTIES.produceServerId(produceServerId);

            String consumeGroupId = propertyReader.getStringKey(CONSUME_GROUP_ID);
            PROPERTIES.consumeGroupId(consumeGroupId);

            String produceGroupId = propertyReader.getStringKey(PRODUCE_GROUP_ID);
            PROPERTIES.produceGroupId(produceGroupId);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
