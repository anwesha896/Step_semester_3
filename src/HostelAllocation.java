class HostelRoom {
    int roomNo;
    int beds;
    int occupied;
    // Constructor
    HostelRoom(int roomNo, int beds, int occupied)
    {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }
    // Allot a bed if one is available
    void allot(String name)
    {
        if (occupied < beds)
        {
            occupied++;
            System.out.println(name + " allotted to room " + roomNo);
        }
    }
}
public class HostelAllocation {
    /*
     * The array contains references to HostelRoom objects.
     * Passing the array to a method copies the array reference,
     * not the actual HostelRoom objects. Therefore, changes made
     * to a room inside the method affect the same object.
     */
    static HostelRoom findAvailableRoom(HostelRoom[] rooms)
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
    static void safeAllot(HostelRoom[] rooms, String studentName)
    {
        HostelRoom room = findAvailableRoom(rooms);
        // Check for null before accessing the room
        if (room != null)
        {
            room.allot(studentName);
        }
        else
        {
            System.out.println("No rooms available for " + studentName);
        }
    }
    public static void main(String[] args)
    {
        // Case 1: One room has an available bed
        HostelRoom[] rooms1 =
                {
                        new HostelRoom(214, 3, 2),
                        new HostelRoom(507, 2, 2)
                };

        System.out.println("Rooms: C-214 (2/3), C-507 (2/2)");
        safeAllot(rooms1, "Divya");
        System.out.println();
        // Case 2: All rooms are full
        HostelRoom[] rooms2 =
                {
                        new HostelRoom(214, 3, 3),
                        new HostelRoom(507, 2, 2)
                };
        System.out.println("Rooms: C-214 (3/3), C-507 (2/2)");
        safeAllot(rooms2, "Divya");
    }
}

