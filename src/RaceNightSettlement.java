class SettlementRaceEntry {
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;
    private static int bibCounter = 0;
    private final String entryCode;
    public SettlementRaceEntry(String bibNumber, double entryFee) {
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        bibCounter++;
        entryCode = "RACE-" + bibCounter;
    }
    public void pay(double amount) {
        amountPaid += amount;
    }
    public void pay(double amount, String mode) {
        System.out.println("Paying via " + mode);
        pay(amount);
    }
    public String getEntryCode() {
        return entryCode;
    }
    public static int getBibCounter() {
        return bibCounter;
    }
    public static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'M') {
            return false;
        }
        if (!Character.isDigit(code.charAt(1))) {
            return false;
        }
        if (!Character.isDigit(code.charAt(2))) {
            return false;
        }
        if (!Character.isDigit(code.charAt(3))) {
            return false;
        }
        if (!Character.isUpperCase(code.charAt(4))) {
            return false;
        }
        return true;
    }
}
class SettlementRelayTeamEntry extends SettlementRaceEntry {
    private int teamSize;
    public SettlementRelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }
    public int getTeamSize() {
        return teamSize;
    }
}
public class RaceNightSettlement {
    public static String settleNight(SettlementRaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;
        for (SettlementRaceEntry entry : entries) {
            if (entry == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (entry instanceof SettlementRelayTeamEntry) {
                relay++;
            } else {
                individual++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + relay + " relay | "
                + individual + " individual";
    }
    public static void main(String[] args) {
        SettlementRaceEntry r = new SettlementRaceEntry("BIB2001", 80);
        SettlementRaceEntry eliteEntry = new SettlementRaceEntry("BIB3001", 150);
        SettlementRelayTeamEntry relayEntry = new SettlementRelayTeamEntry("BIB4001", 300, 4);
        r.pay(10, "UPI");
        System.out.println(SettlementRaceEntry.isValidDiscountCode("M123A"));
        System.out.println(SettlementRaceEntry.isValidDiscountCode("M12A"));
        System.out.println(SettlementRaceEntry.isValidDiscountCode("X123A"));
        SettlementRaceEntry[] entries = {eliteEntry, null, relayEntry};
        System.out.println(settleNight(entries));
        System.out.println("Bib Counter: " + SettlementRaceEntry.getBibCounter());
        System.out.println("Entry Code: " + r.getEntryCode());
    }
}