import java.util.Scanner;

public class WordReversalEncoder
{
    static String reverseEachWord(String sentence)
    {
        String[] words = sentence.split(" ");

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++)
        {
            StringBuilder reversed = new StringBuilder();
            // Reverse each word
            for (int j = words[i].length() - 1; j >= 0; j--)
            {
                reversed.append(words[i].charAt(j));
            }
            result.append(reversed);
            // Add space between words
            if (i < words.length - 1)
            {
                result.append(" ");
            }
        }
        return result.toString();
    }
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter sentence: ");
        String sentence = sc.nextLine();
        String result = reverseEachWord(sentence);
        System.out.println(result);
        sc.close();
    }
}

