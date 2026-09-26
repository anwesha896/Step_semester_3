class BrokenStudent {
    /*
     * These fields are static, so they belong to the class,
     * not to individual student objects.
     *
     * name is wrong as static because every student should
     * have a different name.
     *
     * regNo is wrong as static because every student should
     * have a different registration number.
     *
     * attendance is wrong as static because every student
     * should have independent attendance.
     */
    static String name;
    static String regNo;
    static int attendance;
    BrokenStudent(String name, String regNo, int attendance)
    {
        BrokenStudent.name = name;
        BrokenStudent.regNo = regNo;
        BrokenStudent.attendance = attendance;
    }
}
class Student
{
    // Instance fields: each student has their own values
    String name;
    String regNo;
    int attendance;
    // Static fields: common to all students
    static String university = "SRM";
    static int admissionCount = 0;

    // Constructor
    Student(String name, int attendance)
    {
        this.name = name;
        this.attendance = attendance;

        admissionCount++;
        this.regNo = "RA2311003010" + (10 + admissionCount);
    }
    // Instance method
    void printIdCard() {
        System.out.println(name + " | " + regNo);
    }
    // Static method
    static void printTotalAdmissions() {
        System.out.println("Students admitted so far: "
                + admissionCount);
    }
}
public class StaticBoundaryDemo {
    public static void main(String[] args) {
        // ---------------- BROKEN VERSION ----------------
        System.out.println("Broken version:");
        BrokenStudent student1 =
                new BrokenStudent("Ravi", "RA101", 82);
        BrokenStudent student2 =
                new BrokenStudent("Meera", "RA102", 68);
        System.out.println(student1.name);
        System.out.println(student2.name);
        System.out.println();
        // ---------------- FIXED VERSION ----------------
        System.out.println("Fixed version:");
        Student s1 =
                new Student("Ravi", 82);
        Student s2 =
                new Student("Meera", 68);
        s1.printIdCard();
        s2.printIdCard();
        Student.printTotalAdmissions();
    }
}


