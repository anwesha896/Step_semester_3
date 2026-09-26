import java.util.Arrays;
class TemperatureTracker {
    private double[] readings;
    private int count;
    public TemperatureTracker(double[] initialReadings) {
        readings = new double[500];
        count = 0;
        for (int i = 0; i < initialReadings.length; i++) {
            recordReading(initialReadings[i]);
        }
    }
    public void recordReading(double reading) {
        if (reading <= 0 || reading > 45) {
            return;
        }
        if (count < 500) {
            readings[count] = reading;
            count++;
        }
    }
    public double getAverage() {
        if (count == 0) {
            return 0.0;
        }
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += readings[i];
        }
        return sum / count;
    }
    public double[] getAllReadings() {
        return Arrays.copyOf(readings, count);
    }
    public static void main(String[] args) {
        TemperatureTracker tracker = new TemperatureTracker(new double[]{36.5, -2, 37.1});
        System.out.println(Arrays.toString(tracker.getAllReadings()));
        double[] copy = tracker.getAllReadings();
        copy[0] = 999;
        System.out.println(Arrays.toString(tracker.getAllReadings()));
        tracker.recordReading(38.2);
        tracker.recordReading(50);
        tracker.recordReading(0);
        System.out.println(Arrays.toString(tracker.getAllReadings()));
        System.out.println("Average: " + tracker.getAverage());
    }
}