import java.time.LocalDate;

abstract class LeaveEmployee {
    private String name;

    public LeaveEmployee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean isLeaveAllowed(LocalDate startDate,
                                           LocalDate endDate);
}

class FullTimeLeaveEmployee extends LeaveEmployee {

    public FullTimeLeaveEmployee(String name) {
        super(name);
    }

    @Override
    public boolean isLeaveAllowed(LocalDate startDate,
                                  LocalDate endDate) {
        return true;
    }
}

class PartTimeLeaveEmployee extends LeaveEmployee {

    public PartTimeLeaveEmployee(String name) {
        super(name);
    }

    @Override
    public boolean isLeaveAllowed(LocalDate startDate,
                                  LocalDate endDate) {

        long days = endDate.toEpochDay() - startDate.toEpochDay();

        return days <= 5;
    }
}

class ContractLeaveEmployee extends LeaveEmployee {

    public ContractLeaveEmployee(String name) {
        super(name);
    }

    @Override
    public boolean isLeaveAllowed(LocalDate startDate,
                                  LocalDate endDate) {

        long days = endDate.toEpochDay() - startDate.toEpochDay();

        return days <= 3;
    }
}

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

class EmployeeLeaveRequest {
    private LeaveEmployee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;

    public EmployeeLeaveRequest(LeaveEmployee employee,
                                LocalDate startDate,
                                LocalDate endDate) {

        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
    }

    public LeaveEmployee getEmployee() {
        return employee;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public boolean changeStatus(LeaveStatus newStatus) {

        if (status != LeaveStatus.PENDING) {
            System.out.println(
                    "Cannot change status: "
                            + status
                            + " request cannot revert to Pending."
            );
            return false;
        }

        if (newStatus == LeaveStatus.PENDING) {
            return true;
        }

        status = newStatus;
        return true;
    }

    public void printRequest() {
        System.out.println(
                "Leave request submitted by "
                        + employee.getName()
                        + " for "
                        + startDate
                        + " to "
                        + endDate
                        + ". Status: "
                        + status
        );
    }
}

class LeaveApprovalService {

    public EmployeeLeaveRequest submitRequest(
            LeaveEmployee employee,
            LocalDate startDate,
            LocalDate endDate) {

        if (!startDate.isBefore(endDate)) {
            System.out.println("Invalid leave dates.");
            return null;
        }

        if (!employee.isLeaveAllowed(startDate, endDate)) {
            System.out.println(
                    "Leave request rejected due to employee leave policy."
            );
            return null;
        }

        EmployeeLeaveRequest request =
                new EmployeeLeaveRequest(
                        employee,
                        startDate,
                        endDate
                );

        request.printRequest();

        return request;
    }

    public void approveRequest(EmployeeLeaveRequest request) {

        if (request != null
                && request.changeStatus(LeaveStatus.APPROVED)) {

            System.out.println(
                    "Leave request for "
                            + request.getEmployee().getName()
                            + " approved. Status: "
                            + request.getStatus()
            );
        }
    }

    public void rejectRequest(EmployeeLeaveRequest request) {

        if (request != null
                && request.changeStatus(LeaveStatus.REJECTED)) {

            System.out.println(
                    "Leave request for "
                            + request.getEmployee().getName()
                            + " rejected. Status: "
                            + request.getStatus()
            );
        }
    }
}

public class EmployeeLeaveManagement {

    public static void main(String[] args) {

        LeaveEmployee john =
                new FullTimeLeaveEmployee("John Doe");

        LeaveEmployee jane =
                new PartTimeLeaveEmployee("Jane Smith");

        LeaveApprovalService service =
                new LeaveApprovalService();

        EmployeeLeaveRequest johnRequest =
                service.submitRequest(
                        john,
                        LocalDate.of(2024, 10, 10),
                        LocalDate.of(2024, 10, 12)
                );

        service.approveRequest(johnRequest);

        EmployeeLeaveRequest janeRequest =
                service.submitRequest(
                        jane,
                        LocalDate.of(2024, 11, 1),
                        LocalDate.of(2024, 11, 5)
                );

        if (johnRequest != null) {
            johnRequest.changeStatus(LeaveStatus.PENDING);
        }
    }
}