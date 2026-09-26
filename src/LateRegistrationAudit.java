import java.util.Arrays;
class Event{
    protected double basePrice;
    private double[] lateFeeHistory;
    private int feeCount;
    public Event(double basePrice) {
        this.basePrice = basePrice;
        lateFeeHistory = new double[10];
        feeCount = 0;}
    public void pay(double amount) {
        basePrice -= amount;}
    public double getBalanceDue() {
        return basePrice;
    }
    protected void applyLateFee(double amount) {
        basePrice += amount;
        lateFeeHistory[feeCount++] = amount;
    }
    public double[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, feeCount);
    }
}
class Ticket extends Event {
    public Ticket(double basePrice) {
        super(basePrice);
    }
    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}
public class LateRegistrationAudit {
    public static void main(String[] args) {
        Ticket w = new Ticket(1200);
        w.pay(1200);
        w.applyLateFee(100);
        System.out.println(w.getBalanceDue());
        double[] history = w.getLateFeeHistory();
        System.out.println(Arrays.toString(history));
        history[0] = 999;
        System.out.println(Arrays.toString(w.getLateFeeHistory()));
    }
}


