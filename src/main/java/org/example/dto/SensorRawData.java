package org.example.dto;

public class SensorRawData {
    private String sensorId;
    private String time;
    private double[] xAxis;
    private double[] yAxis;
    private double[] zAxis;
    private String description;
    private String group;
    private Integer samplePoints;
    private Integer sampleRate;
    private Double sampleRateScale;
    private Double temperature;

    public SensorRawData(String sensorId, String time, double[] xAxis, double[] yAxis, double[] zAxis, String description, String group, Integer samplePoints, Integer sampleRate, Double sampleRateScale, Double temperature) {
        this.sensorId = sensorId;
        this.time = time;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.zAxis = zAxis;
        this.description = description;
        this.group = group;
        this.samplePoints = samplePoints;
        this.sampleRate = sampleRate;
        this.sampleRateScale = sampleRateScale;
        this.temperature = temperature;
    }

    public SensorRawData() {
    }

    public String getSensorId() {
        return sensorId;
    }

    public String getTime() {
        return time;
    }

    public double[] getxAxis() {
        return xAxis;
    }

    public double[] getyAxis() {
        return yAxis;
    }

    public double[] getzAxis() {
        return zAxis;
    }

    public String getDescription() {
        return description;
    }

    public String getGroup() {
        return group;
    }

    public Integer getSamplePoints() {
        return samplePoints;
    }

    public Integer getSampleRate() {
        return sampleRate;
    }

    public Double getSampleRateScale() {
        return sampleRateScale;
    }

    public Double getTemperature() {
        return temperature;
    }
}
