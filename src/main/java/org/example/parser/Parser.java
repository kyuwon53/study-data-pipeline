package org.example.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.SensorData;
import org.example.dto.SensorRawData;

import java.util.Queue;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.example.util.Calculator.getCrest;
import static org.example.util.Calculator.getP2p;
import static org.example.util.Calculator.getRms;

public class Parser implements Runnable {
    private final String data;
    private final Queue queue;

    public Parser(String data, Queue queue) {
        this.data = data;
        this.queue = queue;
    }

    @Override
    public void run() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

            SensorRawData sensorRawData = objectMapper.readValue(data, SensorRawData.class);
            double[] xAxis = sensorRawData.getxAxis();
            double[] yAxis = sensorRawData.getyAxis();
            double[] zAxis = sensorRawData.getzAxis();

            double xRms = getRms(xAxis);
            double yRms = getRms(yAxis);
            double zRms = getRms(zAxis);

            double xP2p = getP2p(xAxis);
            double yP2p = getP2p(yAxis);
            double zP2p = getP2p(zAxis);

            double xCrest = getCrest(xRms, xP2p);
            double yCrest = getCrest(yRms, yP2p);
            double zCrest = getCrest(zRms, zP2p);

            System.out.printf("Calculate Sensor Data: xRms[ %f ] , yRms[ %f ], zRms[ %f ], xP2p[ %f ], yP2p[ %f ], zP2p[ %f ], xCrest[ %f ], yCrest[ %f ], zCrest[ %f ]\n", xRms, yRms, zRms, xP2p, yP2p, zP2p, xCrest, yCrest, zCrest);

            String time = sensorRawData.getTime();
            queue.add(new SensorData(sensorRawData.getSensorId(), time, xRms, yRms, zRms, xP2p, yP2p, zP2p, xCrest, yCrest, zCrest));
            System.out.println("queue size : " + queue.size());

        } catch (JsonProcessingException e) {
            System.out.println(e);
        }

    }

}
