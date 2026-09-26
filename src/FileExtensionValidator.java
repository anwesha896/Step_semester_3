import java.util.Scanner;
public class FileExtensionValidator
{
    static String validateFileExtension(String filename)
    {
        // Find the last '.'
        int dotIndex = filename.lastIndexOf('.');
        // Check if filename has an extension
        if (dotIndex == -1)
        {
            return "Rejected — invalid file type";
        }
        // Extract extension
        String extension = filename.substring(dotIndex + 1);
        // Check accepted extensions
        if (extension.equalsIgnoreCase("pdf") ||
                extension.equalsIgnoreCase("docx") ||
                extension.equalsIgnoreCase("zip"))
        {
            return "Accepted";
        }
        else
        {
            return "Rejected — invalid file type";
        }
    }
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter filename: ");
        String filename = sc.nextLine();
        System.out.println(validateFileExtension(filename));
        sc.close();
    }
}

