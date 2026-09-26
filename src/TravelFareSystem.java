class TravelFare {
    private String tripId;
    private double totalFare;
    private int passengerCount;
    public TravelFare(String tripId, double totalFare, int passengerCount)
    {
        if (totalFare < 0) {
            throw new IllegalArgumentException("Fare cannot be negative");
        }
        if (passengerCount <= 0) {
            throw new IllegalArgumentException(
                    "Passenger count must be positive");
        }
        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }
    public TravelFare(String tripId, double totalFare) {
        this(tripId, totalFare, 1);
    }
    public TravelFare(String tripId) {
        this(tripId, 0.0, 1);
    }
    double[] fareBreakdown() {
        double[] shares = new double[passengerCount];
        if (totalFare == 0) {
            return shares;
        }
        double share = Math.floor((totalFare / passengerCount) * 100) / 100;
        double amountAssigned = 0;
        for (int i = 0; i < passengerCount - 1; i++) {
            shares[i] = share;
            amountAssigned += share;
        }
        shares[passengerCount - 1] = Math.round((totalFare - amountAssigned) * 100) / 100.0;
        return shares;
    }
    boolean isConfirmationOverdue(int confirmed, int expected) {
        return confirmed < expected;
    }
}
public class TravelFareSystem {
    public static void main(String[] args)
    {
        TravelFare trip1 = new TravelFare("TRIP001", 100000, 3);
        double[] result1 = trip1.fareBreakdown();
        System.out.println("TRIP001:");
        for (int i = 0; i < result1.length; i++) {
            System.out.printf("%.2f ", result1[i]);
        }
        System.out.println();
        TravelFare trip2 = new TravelFare("TRIP002", 5000);
        double[] result2 = trip2.fareBreakdown();
        System.out.println("TRIP002:");
        for (int i = 0; i < result2.length; i++) {
            System.out.printf("%.2f ", result2[i]);
        }
        System.out.println();
        TravelFare trip3 = new TravelFare("TRIP003");
        double[] result3 = trip3.fareBreakdown();
        System.out.println("TRIP003:");
        for (int i = 0; i < result3.length; i++) {
            System.out.printf("%.2f ", result3[i]);
        }
        System.out.println();
        System.out.println("Confirmation overdue: " + trip1.isConfirmationOverdue(2, 3));
        System.out.println("Confirmation overdue: " + trip1.isConfirmationOverdue(3, 3));
        try {
            TravelFare invalid = new TravelFare("TRIP004", -5000, 3);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Invalid fare rejected");
        }
    }
}

