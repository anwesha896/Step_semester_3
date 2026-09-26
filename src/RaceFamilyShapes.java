class FamilyRaceEntry {
    protected String bibNumber;
    protected double entryFee;
    public FamilyRaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
    }
    public void pay(double amount) {
        entryFee -= amount;
    }
    public double getBalanceDue() {
        return entryFee;
    }
    public void announce() {
        System.out.println("Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue());
    }
}
class FamilyRunnerEntry extends FamilyRaceEntry {
    protected String category;
    public FamilyRunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }
    @Override
    public void announce() {
        System.out.println("Runner Entry | Bib: " + bibNumber + " | Category: " + category + " | Balance: " + getBalanceDue());
    }
}
class FamilyEliteRunnerEntry extends FamilyRunnerEntry {
    private double sponsorBonus;
    public FamilyEliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }
    @Override
    public void announce() {
        System.out.println("Elite Runner | Bib: " + bibNumber + " | Category: " + category +
                " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + getBalanceDue());
    }
}
class FamilyRelayTeamEntry extends FamilyRaceEntry {
    private int teamSize;
    public FamilyRelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }
    @Override
    public void announce() {
        System.out.println("Relay Team | Bib: " + bibNumber + " | Team Size: " + teamSize + " | Balance: " + getBalanceDue());
    }
}
public class RaceFamilyShapes {
    public static String classifyGeneration(FamilyRaceEntry entry) {
        if (entry instanceof FamilyEliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (entry instanceof FamilyRelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        }
        return "Runner/Base entry";
    }
    public static double getTotalBalanceDue(FamilyRaceEntry[] entries) {
        double total = 0;
        for (FamilyRaceEntry entry : entries) {
            total += entry.getBalanceDue();
        }
        return total;
    }
    public static void main(String[] args) {
        FamilyRunnerEntry runnerEntry = new FamilyRunnerEntry("BIB2001", 80, "Open 10K");
        FamilyEliteRunnerEntry eliteEntry = new FamilyEliteRunnerEntry("BIB3001", 150,
                "Elite Full Marathon", 500);
        FamilyRelayTeamEntry relayEntry = new FamilyRelayTeamEntry("BIB4001", 300, 4);
        runnerEntry.announce();
        eliteEntry.announce();
        relayEntry.announce();
        System.out.println(classifyGeneration(eliteEntry));
        System.out.println(classifyGeneration(relayEntry));
        FamilyRaceEntry[] entries = {runnerEntry, eliteEntry, relayEntry};
        System.out.println(getTotalBalanceDue(entries));
    }
}

