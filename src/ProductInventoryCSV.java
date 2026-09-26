import java.util.Scanner;

public class ProductInventoryCSV
{
    static void parseInventoryRecord(String csvLine)
    {
        // Split the CSV line using comma
        String[] fields = csvLine.split(",");

        // Check whether exactly 3 fields are present
        if (fields.length != 3)
        {
            System.out.println("Invalid Record");
        }
        else
        {
            System.out.println("Product: " + fields[0]
                    + " | SKU: " + fields[1]
                    + " | Qty: " + fields[2]);
        }
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter inventory record: ");
        String csvLine = sc.nextLine();

        parseInventoryRecord(csvLine);

        sc.close();
    }
}
