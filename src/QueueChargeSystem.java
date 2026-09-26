final class QueueCharge {
    private final double minimumSurgePercent;
    // Constructor
    public QueueCharge(double minimumSurgePercent) {
        this.minimumSurgePercent = minimumSurgePercent;
    }
    // final method: calculation cannot be overridden
    final double calculateSurgeFee(double orderValue, int delayMinutes) {
        // Validation at calculation time
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Order value and delay cannot be negative");
        }
        // No delay means no surge fee
        if (delayMinutes == 0) {
            return 0.0;
        }
        double fee = 0.0;
        // Minutes 1-5 → 0.5% per minute
        int firstTier = Math.min(delayMinutes, 5);
        fee += firstTier * orderValue * 0.005;
        // Minutes 6-15 → 1% per minute
        if (delayMinutes > 5) {
            int secondTier = Math.min(delayMinutes - 5, 10);
            fee += secondTier * orderValue * 0.01;
        }
        // Minute 16 onwards → 2% per minute
        if (delayMinutes > 15) {
            int thirdTier = delayMinutes - 15;
            fee += thirdTier * orderValue * 0.02;
        }
        // Minimum surge floor
        double minimumFee = orderValue * minimumSurgePercent / 100;
        if (fee < minimumFee) {
            fee = minimumFee;
        }
        return fee;
    }
}
public class QueueChargeSystem {
    public static void main(String[] args) {
        QueueCharge calculator = new QueueCharge(1.0);
        System.out.println("Surge Fee: Rs " + calculator.calculateSurgeFee(500, 0));
        System.out.println("Surge Fee: Rs " + calculator.calculateSurgeFee(500, 1));
        System.out.println("Surge Fee: Rs " + calculator.calculateSurgeFee(500, 16));
    }
}


