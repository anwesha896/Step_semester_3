abstract class RentalVehicle {
    private String vehicleName;
    private boolean available;

    public RentalVehicle(String vehicleName) {
        this.vehicleName = vehicleName;
        this.available = true;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract double calculateCharge(int days);
}

class StandardRentalCar extends RentalVehicle {

    public StandardRentalCar(String vehicleName) {
        super(vehicleName);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 50.0;
    }
}

class LuxuryRentalCar extends RentalVehicle {

    public LuxuryRentalCar(String vehicleName) {
        super(vehicleName);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 100.0;
    }
}

class SuvRentalVehicle extends RentalVehicle {

    public SuvRentalVehicle(String vehicleName) {
        super(vehicleName);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 75.0;
    }
}

class RentalCustomer {
    private String name;

    public RentalCustomer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class VehicleRental {
    private RentalCustomer customer;
    private RentalVehicle vehicle;
    private int days;
    private double totalCharge;
    private boolean active;

    public VehicleRental(RentalCustomer customer, RentalVehicle vehicle, int days) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
        this.totalCharge = vehicle.calculateCharge(days);
        this.active = true;
    }

    public RentalVehicle getVehicle() {
        return vehicle;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public boolean isActive() {
        return active;
    }

    public void closeRental() {
        active = false;
    }
}

class VehicleRentalService {

    public VehicleRental rentVehicle(
            RentalCustomer customer,
            RentalVehicle vehicle,
            int days) {

        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getVehicleName()
                    + " is currently unavailable.");
            return null;
        }

        if (days <= 0) {
            System.out.println("Rental duration must be positive.");
            return null;
        }

        VehicleRental rental =
                new VehicleRental(customer, vehicle, days);

        vehicle.setAvailable(false);

        System.out.printf(
                "%s rented for %d days. Total charge: $%.2f%n",
                vehicle.getVehicleName(),
                days,
                rental.getTotalCharge()
        );

        return rental;
    }

    public void returnVehicle(VehicleRental rental) {

        if (rental == null || !rental.isActive()) {
            return;
        }

        RentalVehicle vehicle = rental.getVehicle();

        rental.closeRental();
        vehicle.setAvailable(true);

        System.out.println(
                vehicle.getVehicleName()
                        + " returned. Now available."
        );
    }
}

public class VehicleRentalSystem {

    public static void main(String[] args) {

        RentalCustomer customer =
                new RentalCustomer("Anwesha");

        RentalVehicle luxuryCar =
                new LuxuryRentalCar("Luxury Car A");

        RentalVehicle standardCar =
                new StandardRentalCar("Standard Car B");

        VehicleRentalService service =
                new VehicleRentalService();

        VehicleRental luxuryRental =
                service.rentVehicle(
                        customer,
                        luxuryCar,
                        3
                );

        VehicleRental standardRental =
                service.rentVehicle(
                        customer,
                        standardCar,
                        5
                );

        service.returnVehicle(luxuryRental);
    }
}