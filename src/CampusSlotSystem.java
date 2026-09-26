class CampusSlot {
    String orderId;
    String timeSlot;
    // Main constructor
    public CampusSlot(String orderId, String timeSlot) {
        this.orderId = orderId;
        this.timeSlot = timeSlot;
    }
    // Constructor chaining
    public CampusSlot(String orderId) {
        this(orderId, "ASAP");
    }
    boolean isPeakHour() {
        if (timeSlot.equals("12:00-13:00") || timeSlot.equals("13:00-14:00") || timeSlot.equals("19:00-20:00")
                || timeSlot.equals("20:00-21:00")) {
            return true;
        }
        return false;
    }
}
public class CampusSlotSystem {
    public static void main(String[] args) {
        CampusSlot slot1 = new CampusSlot("ORD101", "13:00-14:00");
        CampusSlot slot2 = new CampusSlot("ORD102");
        System.out.println("ORD101 Peak Hour: " + slot1.isPeakHour());
        System.out.println("ORD102 Time Slot: " + slot2.timeSlot);
        System.out.println("ORD102 Peak Hour: " + slot2.isPeakHour());
    }
}



