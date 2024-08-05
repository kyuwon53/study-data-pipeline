package org.example.dto;

public class SensorData {
    private String sensorId;
    private String timestamp;
    private double xRms;
    private double yRms;
    private double zRms;
    private double xP2p;
    private double yP2p;
    private double zP2p;
    private double xCrest;
    private double yCrest;
    private double zCrest;

    public SensorData(
            String sensorId, String timestamp,
            double xRms, double yRms, double zRms,
            double xP2p, double yP2p, double zP2p,
            double xCrest, double yCrest, double zCrest
    ) {
        this.sensorId = sensorId;
        this.timestamp = timestamp;
        this.xRms = xRms;
        this.yRms = yRms;
        this.zRms = zRms;
        this.xP2p = xP2p;
        this.yP2p = yP2p;
        this.zP2p = zP2p;
        this.xCrest = xCrest;
        this.yCrest = yCrest;
        this.zCrest = zCrest;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "sensorId='" + sensorId + '\'' +
                ", timestamp=" + timestamp +
                ", xRms=" + xRms +
                ", yRms=" + yRms +
                ", zRms=" + zRms +
                ", xP2p=" + xP2p +
                ", yP2p=" + yP2p +
                ", zP2p=" + zP2p +
                ", xCrest=" + xCrest +
                ", yCrest=" + yCrest +
                ", zCrest=" + zCrest +
                '}';
    }

    public String getSensorId() {
        return sensorId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getxRms() {
        return xRms;
    }

    public double getyRms() {
        return yRms;
    }

    public double getzRms() {
        return zRms;
    }

    public double getxP2p() {
        return xP2p;
    }

    public double getyP2p() {
        return yP2p;
    }

    public double getzP2p() {
        return zP2p;
    }

    public double getxCrest() {
        return xCrest;
    }

    public double getyCrest() {
        return yCrest;
    }

    public double getzCrest() {
        return zCrest;
    }
}
