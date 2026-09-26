class NightEventTicket{
    protected double basePrice;
    public NightEventTicket(double basePrice) {
        this.basePrice = basePrice;}
    public double getBalanceDue() {
        return basePrice;
    }
    public String printTicket() {
        return "Standard | Balance: " + getBalanceDue();
    }
}
class NightWorkshopTicket extends NightEventTicket {
    private String track;
    public NightWorkshopTicket(double basePrice, String track) {
        super(basePrice);
        this.track = track;
    }
    public String getTrack() {
        return track;
    }
    @Override
    public String printTicket() {
        return "Workshop | Track: " + track + " | Balance: " + getBalanceDue();
    }
}
public class NightlyTicketAnnouncer {
    public static String batchPrint(NightEventTicket[] tickets) {
        StringBuilder report = new StringBuilder();
        for (NightEventTicket ticket : tickets) {
            report.append(ticket.printTicket());
            if (ticket instanceof NightWorkshopTicket) {
                NightWorkshopTicket workshop = (NightWorkshopTicket) ticket;
                report.append(" [Track via downcast: ").append(workshop.getTrack()).append("]");
            }
            report.append(" | ");
        }
        return report.toString();
    }
    public static void main(String[] args) {
        NightEventTicket[] tickets = {new NightEventTicket(500), new NightWorkshopTicket(1200, "AI/ML")};
        System.out.println(batchPrint(tickets));
    }
}

