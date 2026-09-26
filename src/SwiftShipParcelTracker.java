import java.util.ArrayList;
import java.util.List;

// Shipping charge abstraction
interface SwiftShippingRule {
    double calculateCharge(double weight);
    String getShippingType();
}

// Standard shipping
class SwiftStandardShipping implements SwiftShippingRule {

    public double calculateCharge(double weight) {
        return 40 + (10 * weight);
    }

    public String getShippingType() {
        return "Standard";
    }
}

// Express shipping
class SwiftExpressShipping implements SwiftShippingRule {

    public double calculateCharge(double weight) {
        return 80 + (15 * weight);
    }

    public String getShippingType() {
        return "Express";
    }
}

// Fragile shipping reuses the Standard calculation through composition
class SwiftFragileShipping implements SwiftShippingRule {

    private SwiftStandardShipping standardShipping;

    public SwiftFragileShipping() {
        standardShipping = new SwiftStandardShipping();
    }

    public double calculateCharge(double weight) {
        return standardShipping.calculateCharge(weight) + 50;
    }

    public String getShippingType() {
        return "Fragile";
    }
}

// Notification abstraction
interface SwiftNotificationChannel {
    void notifyCustomer(String parcelId, SwiftParcelStatus status);
}

// SMS notification
class SwiftSmsChannel implements SwiftNotificationChannel {

    public void notifyCustomer(
            String parcelId,
            SwiftParcelStatus status) {

        System.out.println(
                "[SMS] " + parcelId
                        + " is now " + status + "."
        );
    }
}

// Email notification
class SwiftEmailChannel implements SwiftNotificationChannel {

    public void notifyCustomer(
            String parcelId,
            SwiftParcelStatus status) {

        System.out.println(
                "[Email] " + parcelId
                        + " is now " + status + "."
        );
    }
}

// Parcel status
enum SwiftParcelStatus {
    BOOKED,
    PICKED_UP,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}

// Customer
class SwiftParcelCustomer {

    private String name;
    private List<SwiftNotificationChannel> channels;

    public SwiftParcelCustomer(String name) {
        this.name = name;
        this.channels = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void subscribeChannel(
            SwiftNotificationChannel channel) {

        channels.add(channel);
    }

    public void notifyStatus(
            String parcelId,
            SwiftParcelStatus status) {

        for (SwiftNotificationChannel channel : channels) {
            channel.notifyCustomer(parcelId, status);
        }
    }
}

// Parcel
class SwiftParcel {

    private String parcelId;
    private SwiftParcelCustomer customer;
    private double weight;
    private SwiftShippingRule shippingRule;
    private SwiftParcelStatus status;

    public SwiftParcel(
            String parcelId,
            SwiftParcelCustomer customer,
            double weight,
            SwiftShippingRule shippingRule) {

        this.parcelId = parcelId;
        this.customer = customer;
        this.weight = weight;
        this.shippingRule = shippingRule;
        this.status = SwiftParcelStatus.BOOKED;
    }

    public String getParcelId() {
        return parcelId;
    }

    public SwiftParcelStatus getStatus() {
        return status;
    }

    public double calculateCharge() {
        return shippingRule.calculateCharge(weight);
    }

    public void notifyBooked() {
        customer.notifyStatus(parcelId, status);
    }

    // All status transition logic stays inside Parcel
    public void changeStatus(SwiftParcelStatus newStatus) {

        if (!isValidTransition(newStatus)) {
            System.out.println(
                    "Invalid transition: "
                            + status + " → " + newStatus
                            + " is not allowed."
            );
            return;
        }

        status = newStatus;

        customer.notifyStatus(parcelId, status);
    }

    private boolean isValidTransition(
            SwiftParcelStatus newStatus) {

        if (status == SwiftParcelStatus.BOOKED
                && newStatus == SwiftParcelStatus.PICKED_UP) {
            return true;
        }

        if (status == SwiftParcelStatus.PICKED_UP
                && newStatus == SwiftParcelStatus.IN_TRANSIT) {
            return true;
        }

        if (status == SwiftParcelStatus.IN_TRANSIT
                && newStatus == SwiftParcelStatus.OUT_FOR_DELIVERY) {
            return true;
        }

        if (status == SwiftParcelStatus.OUT_FOR_DELIVERY
                && newStatus == SwiftParcelStatus.DELIVERED) {
            return true;
        }

        return false;
    }

    public void cancel() {

        if (status != SwiftParcelStatus.BOOKED) {
            System.out.println(
                    "Cancellation failed: "
                            + parcelId
                            + " can be cancelled only while BOOKED."
            );
            return;
        }

        status = SwiftParcelStatus.CANCELLED;

        customer.notifyStatus(parcelId, status);
    }
}

// Parcel service
class SwiftParcelService {

    public SwiftParcel bookParcel(
            String parcelId,
            SwiftParcelCustomer customer,
            double weight,
            SwiftShippingRule shippingRule) {

        SwiftParcel parcel =
                new SwiftParcel(
                        parcelId,
                        customer,
                        weight,
                        shippingRule
                );

        System.out.printf(
                "Parcel %s booked (%s, %.0f kg).%n",
                parcelId,
                shippingRule.getShippingType(),
                weight
        );

        System.out.printf(
                "Charge: ₹%.2f%n",
                parcel.calculateCharge()
        );

        // Notify all subscribed channels about BOOKED
        parcel.notifyBooked();

        return parcel;
    }
}

// Main class
public class SwiftShipParcelTracker {

    public static void main(String[] args) {

        SwiftParcelCustomer customer =
                new SwiftParcelCustomer("Anwesha");

        // Subscribe to SMS and Email
        customer.subscribeChannel(
                new SwiftSmsChannel()
        );

        customer.subscribeChannel(
                new SwiftEmailChannel()
        );

        // Express shipping
        SwiftShippingRule expressShipping =
                new SwiftExpressShipping();

        SwiftParcelService service =
                new SwiftParcelService();

        // Book parcel
        SwiftParcel parcel =
                service.bookParcel(
                        "P101",
                        customer,
                        2,
                        expressShipping
                );

        // Picked up
        parcel.changeStatus(
                SwiftParcelStatus.PICKED_UP
        );

        // Attempt cancellation after pickup
        parcel.cancel();

        // In transit
        parcel.changeStatus(
                SwiftParcelStatus.IN_TRANSIT
        );

        // Invalid attempt: directly from IN_TRANSIT to DELIVERED
        parcel.changeStatus(
                SwiftParcelStatus.DELIVERED
        );
    }
}