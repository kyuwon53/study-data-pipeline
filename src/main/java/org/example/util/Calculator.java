package org.example.util;

public class Calculator {
    private Calculator() {
    }

    public static double getRms(double[] sensorData) {
        if (sensorData == null || sensorData.length == 0) {
            return 0;
        }

        double sumOfSquares = 0;
        for (double value : sensorData) {
            sumOfSquares += value * value;
        }

        double meanOfSquares = sumOfSquares / sensorData.length;
        return Math.sqrt(meanOfSquares);
    }

    public static double getP2p(double[] sensorData) {
        if (sensorData == null || sensorData.length == 0) {
            return 0;
        }

        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;

        for (double value : sensorData) {
            if (value > max) {
                max = value;
            }
            if (value < min) {
                min = value;
            }
        }

        return max - min;
    }

    public static double getCrest(double rms, double p2p) {
        return rms > 0 ? p2p / rms : 0;
    }
}
