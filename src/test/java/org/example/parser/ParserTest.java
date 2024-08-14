package org.example.parser;

import org.example.dto.SensorData;
import org.example.dto.SensorRawData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParserTest {

    @Test
    void parserTest() {
        String data = "{\"sensorId\" : \"56\", \"time\" : \"2024-01-31T09:28:47.815553\", \"xAxis\" : [3.1,2.2], \"yAxis\" : [3.1,2.2], \"zAxis\" : [3.1,2.2]}";

        SensorData sensorData = Parser.parse(data);

        assertAll(
                () -> assertEquals("56", sensorData.getSensorId()),
                () -> assertNotNull(sensorData.getTimestamp()),
                () -> assertNotNull(sensorData.getxRms()),
                () -> assertNotNull(sensorData.getyRms()),
                () -> assertNotNull(sensorData.getzRms()),
                () -> assertNotNull(sensorData.getxP2p()),
                () -> assertNotNull(sensorData.getyP2p()),
                () -> assertNotNull(sensorData.getzP2p()),
                () -> assertNotNull(sensorData.getxCrest()),
                () -> assertNotNull(sensorData.getyCrest()),
                () -> assertNotNull(sensorData.getzCrest())
        );
    }
}
