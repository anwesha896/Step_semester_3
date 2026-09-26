class BusTicket {
    private String passengerName;
    private String destination;
    private boolean checkedIn;
    // Only parameterized constructor
    public BusTicket(String passengerName, String destination)
    {
        // Validate passenger name
        if (passengerName == null || passengerName.trim().isEmpty())
        {
            throw new IllegalArgumentException(
                    "Passenger name cannot be blank");
        }
        // Name should contain only letters and spaces
        for (int i = 0; i < passengerName.length(); i++)
        {
            char ch = passengerName.charAt(i);
            if (!Character.isLetter(ch) && ch != ' ')
            {
                throw new IllegalArgumentException(
                        "Passenger name must contain only letters");
            }
        }
        // Validate destination
        if (destination == null || destination.trim().isEmpty())
        {
            throw new IllegalArgumentException(
                    "Destination cannot be blank");
        }
        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
        this.checkedIn = false;
    }
    // Mark ticket as checked in
    void markCheckedIn()
    {
        if (!checkedIn)
        {
            checkedIn = true;
            System.out.println("Checked in: "
                    + passengerName);
        }
        else
        {
            System.out.println("Already checked in: "
                    + passengerName);
        }
    }
    // Process all bookings
    static void processBatch(String[][] rawBookings)
    {
        int valid = 0;
        int rejected = 0;
        int duplicates = 0;
        String[][] acceptedBookings =
                new String[rawBookings.length][2];
        for (int i = 0; i < rawBookings.length; i++)
        {
            String name = rawBookings[i][0];
            String destination = rawBookings[i][1];
            try
            {
                // Constructor performs validation
                BusTicket ticket =
                        new BusTicket(name, destination);
                // Check duplicate
                boolean duplicate = false;
                for (int j = 0; j < valid; j++)
                {
                    if (acceptedBookings[j][0].equals(ticket.passengerName) && acceptedBookings[j][1].equals(ticket.destination))
                    {
                        duplicate = true;
                        break;
                    }
                }
                if (duplicate) {
                    duplicates++;
                }
                else {
                    acceptedBookings[valid][0] =
                            ticket.passengerName;
                    acceptedBookings[valid][1] =
                            ticket.destination;
                    valid++;
                }
            }
            catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        System.out.println("Valid: " + valid + " | Rejected: " + rejected + " | Duplicates skipped: " + duplicates);
    }
}
public class BusTicketBooking
{
    public static void main(String[] args)
    {
        String[][] rawBookings =
                {{"Divya", "Chennai"}, {"", "Bangalore"}, {"Ravi123", "Pune"}, {"Divya", "Chennai"}, {" ", " "}};
        BusTicket.processBatch(rawBookings);
        // Demonstrating checked-in protection
        BusTicket ticket =
                new BusTicket("Anitha", "Chennai");
        ticket.markCheckedIn();
        ticket.markCheckedIn();
    }
}

