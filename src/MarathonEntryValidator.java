class MarathonRaceEntry {
    protected String bibNumber;
    protected double entryFee;
    public MarathonRaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");}
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
    }
    public void pay(double amount) {
        entryFee -= amount;}
    public double getBalanceDue() {
        return entryFee;}
    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;
        for (String bib : bibNumbers) {
            try {
                new MarathonRaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }
}
class MarathonRunnerEntry extends MarathonRaceEntry {
    private String category;
    public MarathonRunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }
}
public class MarathonEntryValidator {
    public static void main(String[] args) {
        MarathonRunnerEntry r = new MarathonRunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        System.out.println(r.getBalanceDue());
        String[] bibNumbers = {"BIB1", "B1", "BIB2"};
        System.out.println(MarathonRaceEntry.registerBatch(bibNumbers, 80));
    }
}
