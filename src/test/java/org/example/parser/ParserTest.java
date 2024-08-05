package org.example.parser;

import org.junit.jupiter.api.Test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class ParserTest {

    @Test
    void parserTest() {
        String data = "{\"sensorId\" : \"56\", \"time\" : \"2024-01-31T09:28:47.815553\" \"xAxis\" : [3.1,2.2], \"yAxis\" : [3.1,2.2], \"zAxis\" : [3.1,2.2]}";
        Queue queue = new ConcurrentLinkedQueue();
        Parser parser = new Parser(data, queue);
        parser.run();

        System.out.println("queue : " + queue.poll().toString());
        System.out.println(queue.size());
    }
}
