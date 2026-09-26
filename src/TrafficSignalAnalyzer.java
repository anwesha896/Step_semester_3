import java.util.Scanner;
public class TrafficSignalAnalyzer
{
    static void findLongestStreak(String signalLog)
    {
        char longestColor = signalLog.charAt(0);
        int longestLength = 1;
        char currentColor = signalLog.charAt(0);
        int currentLength = 1;
        for (int i = 1; i < signalLog.length(); i++)
        {
            if (signalLog.charAt(i) == currentColor)
            {
                currentLength++;
            }
            else
            {
                currentColor = signalLog.charAt(i);
                currentLength = 1;
            }
            if (currentLength > longestLength)
            {
                longestLength = currentLength;
                longestColor = currentColor;
            }
        }
        System.out.println("Longest Streak: '" + longestColor
                + "' repeated " + longestLength + " times");
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter signal log: ");
        String signalLog = sc.nextLine();
        findLongestStreak(signalLog);
        sc.close();
    }
}

