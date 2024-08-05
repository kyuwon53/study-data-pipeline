package org.example;

import org.example.kafka.MyConsumer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    private static Queue queue = new ConcurrentLinkedQueue<>();
    private static final String DATA_INSERT_SERVER_ID = "192.168.0.25:9091";

    private static final String CONSUME_SERVER_ID = "192.168.0.93:9091,192.168.0.93:9092";
    private static final String PRODUCE_SERVER_ID = "192.168.0.25:9091,192.168.0.25:9092";

    private static final String TOPIC = "broadsens2p";

    public static void main(String[] args) throws InterruptedException {
        System.out.println("[Main: Start] queue size : " + queue.size());
//
//        Thread insertDataProducer = new Thread(new MyProducer(queue, DATA_INSERT_SERVER_ID), "insert-data");
//        insertDataProducer.start();
//        insertDataProducer.join();
//        System.out.println("[Main: Data insert] queue size : " + queue.size());

        Thread consumer = new Thread(new MyConsumer(queue, CONSUME_SERVER_ID, PRODUCE_SERVER_ID, TOPIC), "consumer");

        consumer.start();
    }
}
