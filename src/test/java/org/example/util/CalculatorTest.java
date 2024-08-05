package org.example.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.concurrent.ThreadLocalRandom;

class CalculatorTest {

    @Test
    void calculateP2pByFor() {
        final double[] sensorData = generateRandomDoubleArray(100000000);

        long startTime = System.currentTimeMillis(); // 코드 시작 시간

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
        long endTime = System.currentTimeMillis(); // 코드 끝난 시간

        System.out.println(" for result = " + (max - min));
        long durationTimeSec = endTime - startTime;

        System.out.println(durationTimeSec + "ms"); // 초 단위 변환 출력

        long sortStartTime = System.currentTimeMillis(); // 코드 시작 시간

        /* ascending sort */
        double[] sorted = Arrays.stream(sensorData).sorted().toArray();
        /** peak2peak = MAX(x) - MIN(x) **/
        double sortResult = sorted[sorted.length - 1] - sorted[0];

        long sortEndTime = System.currentTimeMillis(); // 코드 끝난 시간

        System.out.println("sort result = " + sortResult);
        long sortDurationTimeSec = sortEndTime - sortStartTime;

        System.out.println(sortDurationTimeSec + "ms"); // 초 단위 변환 출력


        long streamStartTime = System.currentTimeMillis(); // 코드 시작 시간

        /* ascending sort */
        DoubleSummaryStatistics statistics = Arrays.stream(sensorData).summaryStatistics();
        /** peak2peak = MAX(x) - MIN(x) **/
        double streamResult = statistics.getMax() - statistics.getMin();

        long streamEndTime = System.currentTimeMillis(); // 코드 끝난 시간

        System.out.println("stream result = " + streamResult);
        long streamDurationTimeSec = streamEndTime - streamStartTime;

        System.out.println(streamDurationTimeSec + "ms"); // 초 단위 변환 출력
    }

    @Test
    void calculateP2pBySorted() {

    }

    @Test
    void calculateP2pByStream() {

    }

    static double[] generateRandomDoubleArray(int length) {
        double[] array = new double[length];
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < length; i++) {
            array[i] = random.nextDouble();
        }

        return array;
    }
}
