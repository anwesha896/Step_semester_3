import java.util.Scanner;
public class MaskedPhoneNumber
{
    static String maskPhoneNumber(String phone)
    {
        // Validate length
        if (phone.length() != 10)
        {
            return "Invalid phone number";
        }

        // Validate that all characters are digits
        for (int i = 0; i < phone.length(); i++)
        {
            if (!Character.isDigit(phone.charAt(i)))
            {
                return "Invalid phone number";
            }
        }
        // Get last 4 digits
        String lastFour = phone.substring(6);

        // Create masked number using StringBuilder
        StringBuilder masked = new StringBuilder("XXXXXX");
        masked.insert(6, "-");
        masked.append(lastFour);

        return masked.toString();
    }
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();
        System.out.println(maskPhoneNumber(phone));
        sc.close();
    }
}

