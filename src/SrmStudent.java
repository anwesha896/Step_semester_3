public class SrmStudent
{
    String name;
    String regNo;
    int attendance;
    // Constructor
    SrmStudent(String name, String regNo, int attendance)
    {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }
    // Instance method: updates the attendance of this particular student
    void addAttendanceUpdate(int newAttendance)
    {
        attendance = newAttendance;
    }
    // Instance method: checks eligibility of this particular student
    boolean isEligible()
    {
        return attendance >= 75;
    }
    /*
     * classAverage is static because it calculates the average
     * for the whole class, not for one particular student.
     * isEligible is not static because it checks the attendance
     * of one particular student.
     */

    static double classAverage(SrmStudent[] students)
    {
        int total = 0;
        for (int i = 0; i < students.length; i++)
        {
            total += students[i].attendance;
        }
        return (double) total / students.length;
    }

    public static void main(String[] args)
    {
        SrmStudent[] students = new SrmStudent[5];
        students[0] = new SrmStudent("Ravi", "RA101", 82);
        students[1] = new SrmStudent("Anitha", "RA102", 68);
        students[2] = new SrmStudent("Karthik", "RA103", 91);
        students[3] = new SrmStudent("Meera", "RA104", 74);
        students[4] = new SrmStudent("Suresh", "RA105", 60);
        for (int i = 0; i < students.length; i++)
        {
            if (students[i].isEligible())
            {
                System.out.println(students[i].name + " - "
                        + students[i].attendance + "% - Eligible");
            }
            else
            {
                System.out.println(students[i].name + " - "
                        + students[i].attendance + "% - Detained");
            }
        }

        double average = SrmStudent.classAverage(students);

        System.out.println("Class average: " + average + "%");
    }
}

