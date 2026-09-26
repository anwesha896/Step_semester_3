class FleetAccount {
    private String bookingId;
    private double ticketFare;
    static String depotName;
    // Static block
    static {
        depotName = "SRM Central Depot";
    }
    // Full constructor
    public FleetAccount(String bookingId, double ticketFare) {
        if (ticketFare < 0) {
            throw new IllegalArgumentException("Ticket fare cannot be negative");
        }
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }
    // Provisional constructor
    public FleetAccount(String bookingId) {
        this(bookingId, 0.0);
    }
    // Final penalty calculation
    final double calculatePenalty(int minutesLate) {
        if (minutesLate < 0) {
            throw new IllegalArgumentException("Minutes late cannot be negative");
        }
        // Simple flat rate: 1% of fare per late minute
        return ticketFare * 0.01 * minutesLate;
    }
    void processAccount(double amount, int minutesLate) {
        double penalty = calculatePenalty(minutesLate);
        System.out.println(bookingId + " | Amount: Rs " + amount + " | Penalty: Rs " + penalty);
    }
}
// Sleeper account
class SleeperAccount extends FleetAccount {
    public SleeperAccount(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }
    public SleeperAccount(String bookingId) {
        super(bookingId);
    }
    // Sleeper accounts settle differently
    @Override
    void processAccount(double amount, int minutesLate) {
        double penalty = calculatePenalty(minutesLate);
        // Sleeper accounts pay half the normal penalty
        penalty = penalty / 2;
        System.out.println("Sleeper | Amount: Rs " + amount + " | Penalty: Rs " + penalty);
    }
}
public class FleetReconciliation {
    static void processBatch(FleetAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        // If array lengths don't match, stop before processing.
        if (accounts.length != amounts.length || accounts.length != minutesLateArray.length) {
            System.out.println("Batch rejected: Array lengths do not match");
            return;
        }
        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;
        double grandTotalPenalty = 0.0;
        for (int i = 0; i < accounts.length; i++) {
            // Null safety
            if (accounts[i] == null) {
                nullSkipped++;
                continue;
            }
            try {
                // instanceof decides the account type
                if (accounts[i] instanceof SleeperAccount) {
                    sleeperCount++;
                }
                else {
                    regularCount++;
                }
                double penalty = accounts[i].calculatePenalty(minutesLateArray[i]);
                // Sleeper accounts use half penalty
                if (accounts[i] instanceof SleeperAccount) {
                    penalty = penalty / 2;
                }
                accounts[i].processAccount(amounts[i], minutesLateArray[i]);
                grandTotalPenalty += penalty;
                processed++;
            }
            catch (IllegalArgumentException e) {
                System.out.println("Account skipped: Invalid data");
            }
        }
        System.out.println();
        System.out.println(processed + " processed | " + nullSkipped + " null skipped | " + sleeperCount + " sleeper | "
                + regularCount + " regular | " + "grand total penalties = Rs " + grandTotalPenalty);
    }
    public static void main(String[] args) {
        FleetAccount[] accounts = {new SleeperAccount("BK001", 2000), null,
                        new FleetAccount("BK002", 1200)};

        double[] amounts = {1200, 900, 700};
        int[] minutesLateArray = {10, 5, 0};
        processBatch(accounts, amounts, minutesLateArray);
    }
}


