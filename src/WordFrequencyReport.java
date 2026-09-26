import java.util.*;
public class WordFrequencyReport
{
    static void printFilteredWordFrequency(String feedback)
    {
        // Convert to lowercase and remove punctuation
        String cleaned = feedback.toLowerCase()
                .replace(".", "")
                .replace(",", "");
        // Split into words
        String[] words = cleaned.split("\\s+");
        // Stop words
        String[] stopWords = {"the", "was", "and", "a", "is", "of", "in"};
        HashMap<String, Integer> frequency = new HashMap<>();
        // Count words
        for (int i = 0; i < words.length; i++)
        {
            boolean isStopWord = false;
            // Check whether word is a stop word
            for (int j = 0; j < stopWords.length; j++)
            {
                if (words[i].equals(stopWords[j]))
                {
                    isStopWord = true;
                    break;
                }
            }
            if (!isStopWord)
            {
                frequency.put(words[i],
                        frequency.getOrDefault(words[i], 0) + 1);
            }
        }
        // Convert entries to a list for sorting
        ArrayList<Map.Entry<String, Integer>> list =
                new ArrayList<>(frequency.entrySet());
        // Sort by frequency in descending order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare(Map.Entry<String, Integer> a,
                               Map.Entry<String, Integer> b)
            {
                return b.getValue() - a.getValue();
            }
        });
        // Display result
        for (Map.Entry<String, Integer> entry : list)
        {
            System.out.println(entry.getKey() + ": "
                    + entry.getValue());
        }
    }
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter feedback: ");
        String feedback = sc.nextLine();
        printFilteredWordFrequency(feedback);
        sc.close();
    }
}