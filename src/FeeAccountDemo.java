class FeeAccount
{
    private String regNo;
    private double totalFee;
    private double amountPaid;
    // Constructor
    FeeAccount(String regNo, double totalFee, double amountPaid)
    {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }
    // Pay method
    void pay(double amount)
    {
        if (amount > 0)
        {
            amountPaid += amount;
        }
        else
        {
            System.out.println("Invalid payment amount");
        }
    }
    // Calculate due amount
    double getDue()
    {
        return totalFee - amountPaid;
    }
}
// Hostel account inherits FeeAccount
class HostelFeeAccount extends FeeAccount
{
    HostelFeeAccount(String regNo, double totalFee, double amountPaid)
    {
        super(regNo, totalFee, amountPaid);
    }
    // Pay the amount in two installments
    void payInTwoInstallments(double amount)
    {
        pay(amount / 2);
        pay(amount / 2);
    }
}
// Scholarship account inherits FeeAccount
class ScholarshipFeeAccount extends FeeAccount
{
    private double scholarshipPercent;
    ScholarshipFeeAccount(String regNo, double totalFee,
                          double amountPaid, double scholarshipPercent)
    {
        super(regNo, totalFee, amountPaid);

        if (scholarshipPercent >= 0 && scholarshipPercent <= 100)
        {
            this.scholarshipPercent = scholarshipPercent;
        }
        else
        {
            this.scholarshipPercent = 0;
        }
    }
    // Calculate due after scholarship
    double effectiveDue()
    {
        double due = getDue();

        return due - (due * scholarshipPercent / 100);
    }
}
public class FeeAccountDemo
{
    public static void main(String[] args)
    {
        FeeAccount plain =
                new FeeAccount("RA101", 150000, 0);
        HostelFeeAccount hostel =
                new HostelFeeAccount("RA102", 200000, 0);
        ScholarshipFeeAccount scholarship =
                new ScholarshipFeeAccount("RA103", 180000, 0, 20);
        // Make payments
        plain.pay(150000);
        hostel.payInTwoInstallments(60000);
        // Display details using instanceof
        FeeAccount[] accounts = {plain, hostel, scholarship};
        for (int i = 0; i < accounts.length; i++)
        {
            if (accounts[i] instanceof ScholarshipFeeAccount)
            {
                ScholarshipFeeAccount s =
                        (ScholarshipFeeAccount) accounts[i];
                System.out.println("Scholarship account effective due: Rs "
                        + s.effectiveDue());
            }
            else if (accounts[i] instanceof HostelFeeAccount)
            {
                HostelFeeAccount h =
                        (HostelFeeAccount) accounts[i];
                System.out.println("Hostel account due: Rs "
                        + h.getDue());
            }
            else
            {
                System.out.println("Plain account due: Rs "
                        + accounts[i].getDue());
            }
        }
    }
}


