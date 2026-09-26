abstract class BonusStaffMember {
    private double baseSalary;
    protected double bonusRate;
    // Constructor 1
    public BonusStaffMember(double baseSalary) {
        this(baseSalary, 0.10);
    }
    // Constructor 2
    public BonusStaffMember(double baseSalary, double bonusRate) {
        this.baseSalary = baseSalary;
        this.bonusRate = bonusRate;
    }
    public double getSalary() {
        return baseSalary;
    }
    public void setSalary(double baseSalary) {
        if (baseSalary >= 0) {
            this.baseSalary = baseSalary;
        }
    }
    public abstract double calculateBonus();
}
interface BonusAuditable {
    String auditRecord();
}
class BonusTeamLead extends BonusStaffMember
        implements BonusAuditable {
    private int teamSize;
    // Constructor with default bonus rate
    public BonusTeamLead(double baseSalary, int teamSize) {
        super(baseSalary);
        this.teamSize = teamSize;
    }
    // Constructor with explicit bonus rate
    public BonusTeamLead(
            double baseSalary,
            double bonusRate,
            int teamSize) {
        super(baseSalary, bonusRate);
        this.teamSize = teamSize;
    }
    @Override
    public double calculateBonus() {
        return getSalary() * bonusRate;
    }
    @Override
    public String auditRecord() {
        return "TeamLead audit: " + teamSize + " team members, salary $" + getSalary();
    }
}
public class QuarterlyBonusCalculator {
    static String getAuditIfApplicable(
            BonusStaffMember staff) {
        if (staff instanceof BonusAuditable) {
            BonusAuditable auditable = (BonusAuditable) staff;
            return auditable.auditRecord();
        }
        return "No audit required";
    }
    public static void main(String[] args) {
        BonusTeamLead t = new BonusTeamLead(60000, 5);
        System.out.println(t.calculateBonus());
        BonusTeamLead t2 = new BonusTeamLead(60000, 0.20, 5);
        System.out.println(t2.calculateBonus());
        t.setSalary(-5000);
        System.out.println("Salary after rejected update: " + t.getSalary());
        // Upcasting
        BonusStaffMember ref = t;
        System.out.println(getAuditIfApplicable(ref));
    }
}


