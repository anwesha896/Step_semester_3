class Account
{
    private String regNo;
    private double totalFee;
    private double amountPaid;
    Account(String regNo, double totalFee, double amountPaid)
    {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }
    void pay(double amount)
    {
        if (amount > 0)
        {
            amountPaid += amount;
        }
        else
        {
            System.out.println("Payment rejected: Invalid amount");
        }
    }
    double getDue()
    {
        return totalFee - amountPaid;
    }
}
class HostelAccount extends Account
{
    HostelAccount(String regNo, double totalFee, double amountPaid)
    {
        super(regNo, totalFee, amountPaid);
    }
    void payInTwoInstallments(double amount)
    {
        if (amount > 0)
        {
            pay(amount / 2);
            pay(amount / 2);
        }
        else
        {
            System.out.println("Payment rejected: Invalid amount");
        }
    }
}
class Room
{
    String roomNo;
    int beds;
    int occupied;

    Room(String roomNo, int beds, int occupied)
    {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    void allot(String name)
    {
        if (occupied < beds)
        {
            occupied++;
            System.out.println(name + " allotted to room " + roomNo);
        }
    }
}


class CollegeStudent
{
    String name;
    String regNo;
    HostelAccount feeAccount;
    Room room;

    static int totalStudents = 0;

    CollegeStudent(String name, String regNo, HostelAccount feeAccount)
    {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        this.room = null;
        totalStudents++;
    }
    String fullStatus()
    {
        String roomStatus;
        if (room != null)
        {
            roomStatus = room.roomNo;
        }
        else
        {
            roomStatus = "unallotted";
        }

        return name + " | Due: Rs " + feeAccount.getDue()
                + " | Room: " + roomStatus;
    }
}
public class FeeHostelManagement {
    static Room findAvailableRoom(Room[] rooms)
    {
        for (int i = 0; i < rooms.length; i++)
        {
            if (rooms[i].occupied < rooms[i].beds)
            {
                return rooms[i];
            }
        }
        return null;
    }
    static void safeAllot(Room[] rooms, CollegeStudent student) {
        Room room = findAvailableRoom(rooms);
        if (room != null)
        {
            room.allot(student.name);
            student.room = room;
        }
        else
        {
            System.out.println("No rooms available for "
                    + student.name);
        }
    }
    public static void main(String[] args)
    {
        // Create fee accounts
        HostelAccount fee1 =
                new HostelAccount("RA101", 200000, 0);
        HostelAccount fee2 =
                new HostelAccount("RA102", 200000, 0);
        HostelAccount fee3 =
                new HostelAccount("RA103", 200000, 0);
        // Create three students
        CollegeStudent s1 =
                new CollegeStudent("Ravi", "RA101", fee1);
        CollegeStudent s2 =
                new CollegeStudent("Anitha", "RA102", fee2);
        CollegeStudent s3 =
                new CollegeStudent("Karthik", "RA103", fee3);
        // Create rooms
        Room[] rooms =
                {
                        new Room("C-214", 3, 0),
                        new Room("C-507", 2, 0)
                };
        // Allot rooms to only two students
        safeAllot(rooms, s1);
        safeAllot(rooms, s2);
        // Valid payments
        fee1.pay(60000);
        fee2.pay(20000);
        // Invalid payment
        fee3.pay(-5000);
        // Display full status
        System.out.println();
        System.out.println(s1.fullStatus());
        System.out.println(s2.fullStatus());
        System.out.println(s3.fullStatus());
        // Display total students
        System.out.println("Total students: "
                + CollegeStudent.totalStudents);
    }
}


