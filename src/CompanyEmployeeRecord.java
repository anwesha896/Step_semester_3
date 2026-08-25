public class CompanyEmployeeRecord {
    String name;
    String empId;
    Employee employee;
    ParkingSlot slot;

    static int totalRecords = 0;

    CompanyEmployeeRecord(String name, String empId,
                          Employee employee, ParkingSlot slot) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;
        totalRecords++;
    }

    String fullProfile() {
        double pay;

        if (employee instanceof ManagerEmployee)
            pay = ((ManagerEmployee) employee).effectiveSalary();
        else if (employee instanceof InternEmployee)
            pay = ((InternEmployee) employee).effectiveSalary();
        else
            pay = employee.getSalary();

        String slotInfo;

        if (slot != null)
            slotInfo = slot.slotNo;
        else
            slotInfo = "no parking assigned";

        return name + " | Pay: Rs " + pay + " | Slot: " + slotInfo;
    }

    public static void main(String[] args) {

        Employee manager = new ManagerEmployee(101, "Divya", 70000, 8000);
        Employee employee = new Employee(102, "Karan", 40000);
        Employee intern = new InternEmployee(103, "Meera", 12000, 10000);

        ParkingSlot slot1 = new ParkingSlot("A1", 4, 0);
        ParkingSlot slot2 = new ParkingSlot("A2", 5, 0);

        CompanyEmployeeRecord r1 =
                new CompanyEmployeeRecord("Divya", "E101", manager, slot1);

        CompanyEmployeeRecord r2 =
                new CompanyEmployeeRecord("Karan", "E102", employee, slot2);

        CompanyEmployeeRecord r3 =
                new CompanyEmployeeRecord("Meera", "E103", intern, null);

        ParkingSlot[] slots = {slot1, slot2};

        ParkingSlot s1 = ParkingSlot.findAvailableSlot(slots);
        if (s1 != null)
            s1.occupiedCount++;

        ParkingSlot s2 = ParkingSlot.findAvailableSlot(slots);
        if (s2 != null)
            s2.occupiedCount++;

        System.out.println(r1.fullProfile());
        System.out.println(r2.fullProfile());
        System.out.println(r3.fullProfile());

        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}
