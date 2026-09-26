class AnnouncerRaceEntry {
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;
    AnnouncerRaceEntry(String bibNumber, double entryFee) {
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
    }
    void pay(double amount) {
        amountPaid += amount;
    }
    double getBalanceDue() {
        return entryFee - amountPaid;
    }
    String announce() {
        return "Runner Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
    }
}
class AnnouncerRelayTeamEntry extends AnnouncerRaceEntry {
    private int teamSize;
    AnnouncerRelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }
    int getTeamSize() {
        return teamSize;
    }
    @Override
    String announce() {
        return "Relay Team | Bib: " + bibNumber + " | Team Size: " + teamSize + " | Balance: " + getBalanceDue();
    }
}
public class RaceDayAnnouncer {
    static String announceAll(AnnouncerRaceEntry[] entries) {
        StringBuilder report = new StringBuilder();
        for (AnnouncerRaceEntry entry : entries) {
            // Polymorphic call
            report.append(entry.announce());
            // Safe downcasting
            if (entry instanceof AnnouncerRelayTeamEntry) {
                AnnouncerRelayTeamEntry relay = (AnnouncerRelayTeamEntry) entry;
                report.append(" [Team size via downcast: ").append(relay.getTeamSize()).append("]");
            }
            report.append(" | ");
        }
        return report.toString();
    }
    public static void main(String[] args) {
        AnnouncerRaceEntry runnerEntry = new AnnouncerRaceEntry("BIB2001", 120);
        runnerEntry.pay(30);
        AnnouncerRelayTeamEntry relayEntry = new AnnouncerRelayTeamEntry("BIB4001", 300, 4);
        String result = announceAll(new AnnouncerRaceEntry[] {runnerEntry, relayEntry});
        System.out.println(result);
        // Demonstration of unsafe downcasting:
        AnnouncerRaceEntry plain = new AnnouncerRaceEntry("BIB5001", 50);
        // This compiles, but throws ClassCastException at runtime.
        // AnnouncerRelayTeamEntry bad =
        //         (AnnouncerRelayTeamEntry) plain;
    }
}


