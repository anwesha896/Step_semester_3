class CampusAccount {
    private String studentId;
    private double orderValue;
    static String campusName;
    // Static block for one-time class-level setup
    static {
        campusName = "SRM Campus";
    }
    // Full constructor
    public CampusAccount(String studentId, double orderValue) {
        if (orderValue < 0) {
            throw new IllegalArgumentException("Order value cannot be negative");
        }
        this.studentId = studentId;
        this.orderValue = orderValue;
    }
    // Provisional constructor using this()
    public CampusAccount(String studentId) {
        this(studentId, 0.0);
    }
    // Final surge-fee calculation
    final double calculateSurgeFee(int delayMinutes) {
        if (delayMinutes < 0) {
            throw new IllegalArgumentException("Delay cannot be negative");
        }
        // Simple flat rate: 1% of order value per delayed minute
        return orderValue * 0.01 * delayMinutes;
    }
    void processAccount(CampusAccount account, double amount, int delayMinutes)
    {
        double fee = account.calculateSurgeFee(delayMinutes);
        System.out.println(studentId + " | Amount: Rs " + amount + " | Surge Fee: Rs " + fee);
    }
}
// Premium account
class GoldAccount extends CampusAccount {
    public GoldAccount(String studentId, double orderValue) {
        super(studentId, orderValue);
    }
    public GoldAccount(String studentId) {
        super(studentId);
    }
    // Premium members pay half the normal surge fee
    @Override
    void processAccount(CampusAccount account, double amount, int delayMinutes)
    {
        double fee = account.calculateSurgeFee(delayMinutes);
        fee = fee / 2;
        System.out.println("Premium | Amount: Rs " + amount + " | Surge Fee: Rs " + fee);
    }
}
public class KitchenReconciliation {
    static void processBatch(CampusAccount[] accounts, double[] amounts, int[] delayMinutesArray)
    {
        // Reject the entire batch if parallel arrays don't match.
        // Otherwise, data could be paired with the wrong student.
        if (accounts.length != amounts.length || accounts.length != delayMinutesArray.length)
        {
            System.out.println("Batch rejected: Array lengths do not match");
            return;
        }
        int processed = 0;
        int nullSkipped = 0;
        int premiumCount = 0;
        int regularCount = 0;
        double grandTotalSurgeFee = 0.0;
        for (int i = 0; i < accounts.length; i++) {
            // Null safety
            if (accounts[i] == null) {
                nullSkipped++;
                continue;
            }
            try {
                double fee = accounts[i].calculateSurgeFee(delayMinutesArray[i]);
                // Use instanceof to identify premium accounts
                if (accounts[i] instanceof GoldAccount) {
                    premiumCount++;
                    // Premium accounts pay half the surge fee
                    fee = fee / 2;
                }
                else {
                    regularCount++;
                }
                accounts[i].processAccount(accounts[i], amounts[i], delayMinutesArray[i]);
                grandTotalSurgeFee += fee;
                processed++;
            }
            catch (IllegalArgumentException e) {
                System.out.println("Account skipped: Invalid data");
            }
        }
        System.out.println();
        System.out.println(processed + " processed | " + nullSkipped + " null skipped | " + premiumCount + " premium | "
                        + regularCount + " regular | " + "grand total surge fees = Rs " + grandTotalSurgeFee);
    }
    public static void main(String[] args) {
        CampusAccount[] accounts = {new GoldAccount("STU001", 500), null,
                        new CampusAccount("STU002", 300)};
        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};
        processBatch(accounts, amounts, delayMinutesArray);
    }
}


