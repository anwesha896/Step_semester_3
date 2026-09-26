abstract class CheckoutPaymentMethod {
    private static int transactionCounter = 1000;
    private final String transactionId;
    public CheckoutPaymentMethod() {
        transactionCounter++;
        transactionId = "TXN-" + transactionCounter;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public abstract String processPayment(double amount);
    // Method overloading
    public String processPayment(double amount, String note) {
        return processPayment(amount) + " (" + note + ")";
    }
}
class CheckoutCreditCardPayment extends CheckoutPaymentMethod {
    private String cardNumberLastFour;
    public CheckoutCreditCardPayment(String cardNumberLastFour) {
        this.cardNumberLastFour = cardNumberLastFour;
    }
    @Override
    public String processPayment(double amount) {
        return "Charged $" + amount + " to card ending " + cardNumberLastFour + " - Txn " + getTransactionId();
    }
}
class CheckoutCashPayment extends CheckoutPaymentMethod {
    public CheckoutCashPayment() {
        super();
    }
    @Override
    public String processPayment(double amount) {
        return "Received $" + amount + " in cash - Txn " + getTransactionId();
    }
}
public class CheckoutPaymentHandler {
    static void printConfirmation(
            CheckoutPaymentMethod payment,
            double amount) {
        System.out.println(payment.processPayment(amount));
    }
    static void testUpcasting() {
        CheckoutCreditCardPayment cc = new CheckoutCreditCardPayment("4471");
        // Upcasting: child object stored in parent reference
        CheckoutPaymentMethod ref = cc;
        printConfirmation(ref, 250.0);
    }
    public static void main(String[] args) {
        CheckoutCreditCardPayment cc = new CheckoutCreditCardPayment("4471");
        System.out.println(cc.processPayment(250.0));
        CheckoutCashPayment cash = new CheckoutCashPayment();
        System.out.println(cash.processPayment(40.0));
        System.out.println(cc.processPayment(250.0, "Birthday gift"));
        testUpcasting();
        // This is NOT allowed:
        // CheckoutPaymentMethod payment =
        //         new CheckoutPaymentMethod();
        //
        // Compile-time error because the class is abstract.
    }
}