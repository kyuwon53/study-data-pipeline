package org.example;

import org.example.dto.KafkaProperty;
import org.example.kafka.MyConsumer;
import org.example.reader.PropertyReader;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    private static KafkaProperty PROPERTIES = new KafkaProperty();
    private static Queue queue = new ConcurrentLinkedQueue<>();
    private static final String CONSUME_SERVER_ID = "consume.server.id";
    private static final String PRODUCE_SERVER_ID = "produce.server.id";
    private static final String CONSUME_GROUP_ID = "consume.group.id";
    private static final String PRODUCE_GROUP_ID = "produce.group.id";

    public static void main(String[] args) {
        System.out.println("[Main: Start] queue size : " + queue.size());
        Main main = new Main();
        main.loadProperties();
//
//        Thread insertDataProducer = new Thread(new MyProducer(queue, DATA_INSERT_SERVER_ID), "insert-data");
//        insertDataProducer.start();
//        insertDataProducer.join();
//        System.out.println("[Main: Data insert] queue size : " + queue.size());

        Thread consumer = new Thread(new MyConsumer(queue, PROPERTIES), "consumer");

        consumer.start();
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
