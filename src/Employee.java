public class Employee {
    private int empId;
    private String empName;
    private double salary;

    Employee(int empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    double getSalary() {
        return salary;
    }

    public static void main(String[] args) {
        Employee e1 = new Employee(101, "Aditi", 40000);
        ManagerEmployee e2 = new ManagerEmployee(102, "Rohan", 70000, 8000);
        InternEmployee e3 = new InternEmployee(103, "Meera", 12000, 10000);

        Employee[] employees = {e1, e2, e3};

        for (Employee e : employees) {
            if (e instanceof ManagerEmployee)
                System.out.println("Manager effective pay: Rs " +
                        ((ManagerEmployee) e).effectiveSalary());
            else if (e instanceof InternEmployee)
                System.out.println("Intern effective pay: Rs " +
                        ((InternEmployee) e).effectiveSalary());
            else
                System.out.println("Plain employee pay: Rs " +
                        e.getSalary());
        }
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    ManagerEmployee(int empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    InternEmployee(int empId, String empName, double salary, double stipendCap) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    double effectiveSalary() {
        return Math.min(getSalary(), stipendCap);
    }
}

