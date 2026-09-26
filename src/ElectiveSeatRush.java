import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Credit policy abstraction
interface ElectiveCreditPolicy {
    int getCreditLimit();
    String getStudentType();
}

// Regular student policy
class RushRegularPolicy implements ElectiveCreditPolicy {

    public int getCreditLimit() {
        return 24;
    }

    public String getStudentType() {
        return "Regular";
    }
}

// Honors student policy
class RushHonorsPolicy implements ElectiveCreditPolicy {

    public int getCreditLimit() {
        return 28;
    }

    public String getStudentType() {
        return "Honors";
    }
}

// Exchange student policy
class RushExchangePolicy implements ElectiveCreditPolicy {

    public int getCreditLimit() {
        return 20;
    }

    public String getStudentType() {
        return "Exchange";
    }
}

// Student
class RushStudent {

    private String name;
    private int currentCredits;
    private ElectiveCreditPolicy creditPolicy;

    public RushStudent(
            String name,
            int currentCredits,
            ElectiveCreditPolicy creditPolicy) {

        this.name = name;
        this.currentCredits = currentCredits;
        this.creditPolicy = creditPolicy;
    }

    public String getName() {
        return name;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public int getCreditLimit() {
        return creditPolicy.getCreditLimit();
    }

    public String getStudentType() {
        return creditPolicy.getStudentType();
    }

    public boolean canTakeCredits(int electiveCredits) {
        return currentCredits + electiveCredits
                <= getCreditLimit();
    }

    public void addCredits(int credits) {
        currentCredits += credits;
    }

    public void removeCredits(int credits) {
        currentCredits -= credits;
    }
}

// Enrollment record
class RushEnrollment {

    private RushStudent student;
    private RushElective elective;

    public RushEnrollment(
            RushStudent student,
            RushElective elective) {

        this.student = student;
        this.elective = elective;
    }

    public RushStudent getStudent() {
        return student;
    }

    public RushElective getElective() {
        return elective;
    }
}

// Elective
class RushElective {

    private String name;
    private int credits;
    private int capacity;

    private List<RushEnrollment> enrollments;

    // Waitlist is private to the elective
    private Queue<RushStudent> waitlist;

    public RushElective(
            String name,
            int credits,
            int capacity) {

        this.name = name;
        this.credits = credits;
        this.capacity = capacity;

        enrollments = new LinkedList<>();
        waitlist = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrollmentCount() {
        return enrollments.size();
    }

    public boolean isFull() {
        return enrollments.size() >= capacity;
    }

    public boolean isEnrolled(RushStudent student) {

        for (RushEnrollment enrollment : enrollments) {

            if (enrollment.getStudent() == student) {
                return true;
            }
        }

        return false;
    }

    public boolean isWaitlisted(RushStudent student) {
        return waitlist.contains(student);
    }

    public void addEnrollment(RushStudent student) {

        enrollments.add(
                new RushEnrollment(student, this)
        );

        student.addCredits(credits);
    }

    public void removeEnrollment(RushStudent student) {

        for (int i = 0; i < enrollments.size(); i++) {

            if (enrollments.get(i).getStudent() == student) {

                enrollments.remove(i);
                student.removeCredits(credits);
                return;
            }
        }
    }

    public void addToWaitlist(RushStudent student) {
        waitlist.offer(student);
    }

    public int getWaitlistPosition(RushStudent student) {

        int position = 1;

        for (RushStudent waiting : waitlist) {

            if (waiting == student) {
                return position;
            }

            position++;
        }

        return -1;
    }

    public RushStudent getNextWaitlistedStudent() {
        return waitlist.peek();
    }

    public void removeFromWaitlist() {
        waitlist.poll();
    }
}

// Enrollment service
class RushEnrollmentService {

    public void enroll(
            RushStudent student,
            RushElective elective) {

        // Duplicate enrollment/waitlist check
        if (elective.isEnrolled(student)
                || elective.isWaitlisted(student)) {

            System.out.println(
                    "Enrollment failed: "
                            + student.getName()
                            + " is already enrolled or waitlisted."
            );
            return;
        }

        // Credit limit MUST be checked before capacity
        if (!student.canTakeCredits(
                elective.getCredits())) {

            System.out.println(
                    "Enrollment failed: "
                            + student.getName()
                            + " would exceed the "
                            + student.getStudentType()
                            + " credit limit ("
                            + (student.getCurrentCredits()
                            + elective.getCredits())
                            + "/"
                            + student.getCreditLimit()
                            + ")."
            );
            return;
        }

        // Check seat availability
        if (elective.isFull()) {

            elective.addToWaitlist(student);

            System.out.println(
                    elective.getName()
                            + " is full."
            );

            System.out.println(
                    student.getName()
                            + " added to waitlist (position "
                            + elective.getWaitlistPosition(student)
                            + ")."
            );

            return;
        }

        // Enroll student
        elective.addEnrollment(student);

        System.out.println(
                student.getName()
                        + " enrolled in "
                        + elective.getName()
                        + " (credits: "
                        + student.getCurrentCredits()
                        + "/"
                        + student.getCreditLimit()
                        + ")."
        );
    }

    public void drop(
            RushStudent student,
            RushElective elective) {

        if (!elective.isEnrolled(student)) {

            System.out.println(
                    "Drop failed: "
                            + student.getName()
                            + " is not enrolled."
            );
            return;
        }

        // Drop first
        elective.removeEnrollment(student);

        System.out.println(
                student.getName()
                        + " dropped "
                        + elective.getName()
                        + " (credits: "
                        + student.getCurrentCredits()
                        + "/"
                        + student.getCreditLimit()
                        + ")."
        );

        // Immediately promote next eligible student
        promoteNextStudent(elective);
    }

    private void promoteNextStudent(
            RushElective elective) {

        RushStudent waitingStudent =
                elective.getNextWaitlistedStudent();

        if (waitingStudent == null) {
            return;
        }

        // Credit limit checked AGAIN during promotion
        if (!waitingStudent.canTakeCredits(
                elective.getCredits())) {

            // Remove ineligible student and continue
            elective.removeFromWaitlist();

            System.out.println(
                    "Promotion skipped: "
                            + waitingStudent.getName()
                            + " would exceed the "
                            + waitingStudent.getStudentType()
                            + " credit limit."
            );

            promoteNextStudent(elective);
            return;
        }

        elective.removeFromWaitlist();

        elective.addEnrollment(waitingStudent);

        System.out.println(
                waitingStudent.getName()
                        + " promoted from waitlist and enrolled in "
                        + elective.getName()
                        + " (credits: "
                        + waitingStudent.getCurrentCredits()
                        + "/"
                        + waitingStudent.getCreditLimit()
                        + ")."
        );
    }
}

// Main class
public class ElectiveSeatRush {

    public static void main(String[] args) {

        RushEnrollmentService service =
                new RushEnrollmentService();

        // Create elective
        RushElective cloudComputing =
                new RushElective(
                        "Cloud Computing",
                        4,
                        2
                );

        // Student policies
        ElectiveCreditPolicy regular =
                new RushRegularPolicy();

        ElectiveCreditPolicy honors =
                new RushHonorsPolicy();

        ElectiveCreditPolicy exchange =
                new RushExchangePolicy();

        // Students
        RushStudent asha =
                new RushStudent(
                        "Asha",
                        20,
                        regular
                );

        RushStudent ravi =
                new RushStudent(
                        "Ravi",
                        22,
                        honors
                );

        RushStudent neha =
                new RushStudent(
                        "Neha",
                        12,
                        exchange
                );

        RushStudent kiran =
                new RushStudent(
                        "Kiran",
                        22,
                        regular
                );

        // Enrollment
        service.enroll(
                asha,
                cloudComputing
        );

        service.enroll(
                ravi,
                cloudComputing
        );

        service.enroll(
                neha,
                cloudComputing
        );

        service.enroll(
                kiran,
                cloudComputing
        );

        // Drop Asha and automatically promote
        service.drop(
                asha,
                cloudComputing
        );
    }
}