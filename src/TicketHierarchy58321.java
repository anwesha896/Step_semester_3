class EventTicket58321 {
    protected String attendeeId;
    protected double basePrice;
    public EventTicket58321(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().isEmpty() || attendeeId.length() < 4) {
            throw new IllegalArgumentException("Invalid attendee ID");
        }
        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
    }
    public void pay(double amount) {
        basePrice -= amount;
    }
    public double getBalanceDue() {
        return basePrice;
    }
    public static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;
        for (String id : attendeeIds) {
            try {
                new EventTicket58321(id, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }
}
class WorkshopTicket58321 extends EventTicket58321 {
    private String track;
    public WorkshopTicket58321(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }
}
public class TicketHierarchy58321 {
    public static void main(String[] args) {
        WorkshopTicket58321 w = new WorkshopTicket58321("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println(w.getBalanceDue());
        String[] ids = {"STU1", "ST1", "STU2", " ", "STU3"};
        System.out.println(EventTicket58321.registerBatch(ids, 500));
    }
}

