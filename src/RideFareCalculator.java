
import java.util.Scanner;

abstract class TransportRide {
    double distance;

    TransportRide(double distance) {
        this.distance = distance;
    }

    abstract double calculateFare();

    abstract String getType();
}

class AutoRide extends TransportRide {
    AutoRide(double distance) {
        super(distance);
    }

    double calculateFare() {
        return 30 + 15 * distance;
    }

    String getType() {
        return "Auto";
    }
}

class CabRide extends TransportRide {
    double surge;

    CabRide(double distance, double surge) {
        super(distance);
        this.surge = surge;
    }

    double calculateFare() {
        return (50 + 20 * distance) * surge;
    }

    String getType() {
        return "Cab";
    }
}

class BikeRide extends TransportRide {
    int waiting;

    BikeRide(double distance, int waiting) {
        super(distance);
        this.waiting = waiting;
    }

    double calculateFare() {
        return 20 + 10 * distance + 2 * waiting;
    }

    String getType() {
        return "Bike";
    }
}

public class RideFareCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        TransportRide[] rides = new TransportRide[n];

        for (int i = 0; i < n; i++) {
            String type = sc.next();
            double distance = sc.nextDouble();

            if (type.equalsIgnoreCase("Auto")) {
                rides[i] = new AutoRide(distance);
            } else if (type.equalsIgnoreCase("Cab")) {
                sc.next();
                double surge = sc.nextDouble();
                rides[i] = new CabRide(distance, surge);
            } else if (type.equalsIgnoreCase("Bike")) {
                sc.next();
                int waiting = sc.nextInt();
                rides[i] = new BikeRide(distance, waiting);
            }
        }

        double total = 0;

        for (int i = 0; i < n; i++) {
            double fare = rides[i].calculateFare();

            System.out.println(rides[i].getType() + ": " +
                    (fare == (long) fare ? (long) fare : fare));

            total += fare;
        }

        System.out.println("Total: " +
                (total == (long) total ? (long) total : total));

        sc.close();
    }
}
