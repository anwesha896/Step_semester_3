import java.util.Scanner;

public class PalindromeChecker
{
    // 1. Iterative approach
    static boolean isPalindromeIterative(String text)
    {
        int start = 0;
        int end = text.length() - 1;

        while (start < end)
        {
            if (text.charAt(start) != text.charAt(end))
            {
                return false;
            }

            start++;
            end--;
        }

        return true;
    }

    // 2. Recursive approach
    static boolean isPalindromeRecursive(String text)
    {
        if (text.length() <= 1)
        {
            return true;
        }

        if (text.charAt(0) != text.charAt(text.length() - 1))
        {
            return false;
        }

        return isPalindromeRecursive(text.substring(1, text.length() - 1));
    }

    // 3. Array reversal approach
    static boolean isPalindromeArrayReversal(String text)
    {
        char[] original = text.toCharArray();
        char[] reversed = new char[original.length];

        for (int i = 0; i < original.length; i++)
        {
            reversed[i] = original[original.length - 1 - i];
        }

        return new String(original).equals(new String(reversed));
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = sc.nextLine();

        boolean iterative = isPalindromeIterative(text);
        boolean recursive = isPalindromeRecursive(text);
        boolean arrayReversal = isPalindromeArrayReversal(text);

        System.out.println();

        System.out.println("Iterative: " +
                (iterative ? "Palindrome" : "Not Palindrome"));

        System.out.println("Recursive: " +
                (recursive ? "Palindrome" : "Not Palindrome"));

        System.out.println("Array Reversal: " +
                (arrayReversal ? "Palindrome" : "Not Palindrome"));

        sc.close();
    }
}