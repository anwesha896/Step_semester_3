import java.util.Arrays;
class WithdrawalRaceEntry {
    protected String bibNumber;
    protected double entryFee;
    private double[] lateFeeHistory;
    private int feeCount;
    public WithdrawalRaceEntry(String bibNumber, double entryFee) {
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        lateFeeHistory = new double[10];
        feeCount = 0;
    }
    public void pay(double amount) {
        entryFee -= amount;
    }
    public double getBalanceDue() {
        return entryFee;
    }
    protected void applyLateFee(double amount) {
        entryFee += amount;
        lateFeeHistory[feeCount++] = amount;
    }
    public double[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, feeCount);
    }
}
class WithdrawalRunnerEntry extends WithdrawalRaceEntry {
    private String category;
    public WithdrawalRunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }
    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}
public class LateWithdrawalAudit {
    public static void main(String[] args) {
        WithdrawalRunnerEntry r = new WithdrawalRunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        r.applyLateFee(20);
        System.out.println(r.getBalanceDue());
        double[] history = r.getLateFeeHistory();
        System.out.println(Arrays.toString(history));
        history[0] = 999;
        System.out.println(Arrays.toString(r.getLateFeeHistory()));
    }
}

