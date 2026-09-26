class SettlementEventTicket {
    private static int ticketCounter = 1000;
    private final String ticketId;
    protected double basePrice;
    public SettlementEventTicket(double basePrice) {
        ticketCounter++;
        ticketId = "TCK-" + ticketCounter;
        this.basePrice = basePrice;
    }
    public void pay(double amount) {
        basePrice -= amount;
    }
    public void pay(double amount, String mode) {
        System.out.println("Payment Mode: " + mode);
        pay(amount);
    }
    public double getBalanceDue() {
        return basePrice;
    }
    public static boolean isValidPromoCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'F') {
            return false;
        }
        if (!Character.isDigit(code.charAt(1)) || !Character.isDigit(code.charAt(2)) || !Character.isDigit(code.charAt(3))) {
            return false;
        }
        if (!Character.isUpperCase(code.charAt(4))) {
            return false;
        }
        return true;
    }
    public static int getTicketsIssued() {
        return ticketCounter - 1000;
    }
    public String getTicketId() {
        return ticketId;
    }
}
class SettlementGroupTicket extends SettlementEventTicket {
    private int groupSize;
    public SettlementGroupTicket(double basePrice, int groupSize) {
        super(basePrice);
        this.groupSize = groupSize;
    }
}
public class FestSettlementEngine {
    public static String processNightlySettlement(SettlementEventTicket[] tickets) {
        int processed = 0;
        int skipped = 0;
        int group = 0;
        int individual = 0;
        for (SettlementEventTicket ticket : tickets) {
            if (ticket == null) {
                skipped++;
            } else {
                processed++;
                if (ticket instanceof SettlementGroupTicket) {
                    group++;
                } else {
                    individual++;
                }
            }
        }
        return processed + " processed | " + skipped + " null skipped | " + group + " group | " + individual + " individual";
    }
    public static void main(String[] args) {
        SettlementEventTicket t1 = new SettlementEventTicket(500);
        System.out.println(t1.getTicketId());
        System.out.println(SettlementEventTicket.getTicketsIssued());
        System.out.println(SettlementEventTicket.isValidPromoCode("F123A"));
        System.out.println(SettlementEventTicket.isValidPromoCode("F12A"));
        System.out.println(SettlementEventTicket.isValidPromoCode("X123A"));
        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println(t1.getBalanceDue());
        SettlementEventTicket[] tickets = {new SettlementGroupTicket(2000, 5), null,
                new SettlementEventTicket(500)};
        System.out.println(processNightlySettlement(tickets));
    }
}

