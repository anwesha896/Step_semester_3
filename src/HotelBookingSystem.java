import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

abstract class HotelRoom {
    private String roomName;

    public HotelRoom(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public abstract double calculatePrice(long nights);

    public boolean isAvailable(LocalDate startDate,
                               LocalDate endDate,
                               List<HotelReservation> reservations) {

        for (HotelReservation reservation : reservations) {
            if (reservation.getRoom() == this
                    && reservation.isActive()
                    && startDate.isBefore(reservation.getEndDate())
                    && endDate.isAfter(reservation.getStartDate())) {

                return false;
            }
        }

        return true;
    }
}

class StandardHotelRoom extends HotelRoom {

    public StandardHotelRoom(String roomName) {
        super(roomName);
    }

    @Override
    public double calculatePrice(long nights) {
        return nights * 150.0;
    }
}

class DeluxeHotelRoom extends HotelRoom {

    public DeluxeHotelRoom(String roomName) {
        super(roomName);
    }

    @Override
    public double calculatePrice(long nights) {
        return nights * 200.0;
    }
}

class SuiteHotelRoom extends HotelRoom {

    public SuiteHotelRoom(String roomName) {
        super(roomName);
    }

    @Override
    public double calculatePrice(long nights) {
        return nights * 300.0;
    }
}

class HotelCustomer {
    private String name;

    public HotelCustomer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class HotelReservation {
    private HotelCustomer customer;
    private HotelRoom room;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate cancellationDeadline;
    private double totalPrice;
    private boolean active;

    public HotelReservation(HotelCustomer customer,
                            HotelRoom room,
                            LocalDate startDate,
                            LocalDate endDate,
                            LocalDate cancellationDeadline) {

        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cancellationDeadline = cancellationDeadline;

        long nights = ChronoUnit.DAYS.between(startDate, endDate);
        this.totalPrice = room.calculatePrice(nights);
        this.active = true;
    }

    public HotelRoom getRoom() {
        return room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        active = false;
    }

    public boolean canBeCancelled(LocalDate currentDate) {
        return active && !currentDate.isAfter(cancellationDeadline);
    }
}

class HotelBookingManager {
    private List<HotelReservation> reservations;

    public HotelBookingManager() {
        reservations = new ArrayList<>();
    }

    public HotelReservation bookRoom(HotelCustomer customer,
                                     HotelRoom room,
                                     LocalDate startDate,
                                     LocalDate endDate,
                                     LocalDate cancellationDeadline) {

        if (!startDate.isBefore(endDate)) {
            System.out.println("Invalid reservation dates.");
            return null;
        }

        if (!room.isAvailable(startDate, endDate, reservations)) {
            System.out.println("Booking failed: "
                    + room.getRoomName()
                    + " is not available for "
                    + startDate
                    + " to "
                    + endDate
                    + ".");
            return null;
        }

        HotelReservation reservation =
                new HotelReservation(
                        customer,
                        room,
                        startDate,
                        endDate,
                        cancellationDeadline
                );

        reservations.add(reservation);

        System.out.printf(
                "%s booked from %s to %s. Total price: $%.2f%n",
                room.getRoomName(),
                startDate,
                endDate,
                reservation.getTotalPrice()
        );

        return reservation;
    }

    public void cancelReservation(HotelReservation reservation,
                                  LocalDate currentDate) {

        if (reservation == null || !reservation.isActive()) {
            System.out.println("Reservation cannot be cancelled.");
            return;
        }

        if (!reservation.canBeCancelled(currentDate)) {
            System.out.println("Cancellation deadline has passed.");
            return;
        }

        reservation.cancel();

        System.out.println(
                "Reservation for "
                        + reservation.getRoom().getRoomName()
                        + " cancelled successfully."
        );
    }
}

public class HotelBookingSystem {

    public static void main(String[] args) {

        HotelCustomer customer =
                new HotelCustomer("Anwesha");

        HotelRoom deluxeRoom =
                new DeluxeHotelRoom("Deluxe Room 101");

        HotelRoom standardRoom =
                new StandardHotelRoom("Standard Room 205");

        HotelBookingManager manager =
                new HotelBookingManager();

        LocalDate deluxeStart =
                LocalDate.of(2024, 12, 1);

        LocalDate deluxeEnd =
                LocalDate.of(2024, 12, 5);

        LocalDate standardStart =
                LocalDate.of(2024, 12, 3);

        LocalDate standardEnd =
                LocalDate.of(2024, 12, 7);

        LocalDate cancellationDeadline =
                LocalDate.of(2024, 11, 30);

        HotelReservation deluxeReservation =
                manager.bookRoom(
                        customer,
                        deluxeRoom,
                        deluxeStart,
                        deluxeEnd,
                        cancellationDeadline
                );

        manager.bookRoom(
                customer,
                standardRoom,
                standardStart,
                standardEnd,
                LocalDate.of(2024, 12, 1)
        );

        manager.bookRoom(
                customer,
                deluxeRoom,
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 7),
                cancellationDeadline
        );

        manager.cancelReservation(
                deluxeReservation,
                LocalDate.of(2024, 11, 25)
        );
    }
}