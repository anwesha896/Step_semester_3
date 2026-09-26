final class GatePenalty {
    private final double minimumPenaltyPercent;
    public GatePenalty(double minimumPenaltyPercent) {
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }
    final double calculatePenalty(double ticketFare, int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException(
                    "Fare and minutes late cannot be negative");
        }
        // On-time boarding means no penalty
        if (minutesLate == 0) {
            return 0.0;
        }
        double penalty = 0.0;
        // First 5 minutes → 0.5% per minute
        int firstTier = Math.min(minutesLate, 5);
        penalty += firstTier * ticketFare * 0.005;
        // Minutes 6 to 15 → 1% per minute
        if (minutesLate > 5) {
            int secondTier = Math.min(minutesLate - 5, 10);
            penalty += secondTier * ticketFare * 0.01;
        }
        // Minute 16 onwards → 2% per minute
        if (minutesLate > 15) {
            int thirdTier = minutesLate - 15;
            penalty += thirdTier * ticketFare * 0.02;
        }
        // Minimum penalty floor
        double minimumPenalty = ticketFare * minimumPenaltyPercent / 100;
        if (penalty < minimumPenalty) {
            penalty = minimumPenalty;
        }
        return penalty;
    }
}
public class GatePenaltySystem {
    public static void main(String[] args) {
        GatePenalty calculator = new GatePenalty(1.0);
        System.out.println("Penalty: Rs " + calculator.calculatePenalty(1000, 0));
        System.out.println("Penalty: Rs " + calculator.calculatePenalty(1000, 1));
        System.out.println("Penalty: Rs " + calculator.calculatePenalty(1000, 16));
    }
}
