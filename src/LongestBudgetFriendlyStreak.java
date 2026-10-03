import java.util.Scanner;

public class LongestBudgetFriendlyStreak {

    public static int[] longestStreak(int[] costs, long budget) {

        int left = 0;
        long windowSum = 0;

        int maxLength = 0;
        int bestStart = -1;

        for (int right = 0; right < costs.length; right++) {

            windowSum += costs[right];

            while (windowSum > budget && left <= right) {
                windowSum -= costs[left];
                left++;
            }

            int currentLength = right - left + 1;

            if (currentLength > maxLength) {
                maxLength = currentLength;
                bestStart = left;
            }
        }

        return new int[]{maxLength, bestStart};
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of days: ");
        int n = sc.nextInt();

        int[] costs = new int[n];

        System.out.println("Enter daily costs:");
        for (int i = 0; i < n; i++) {
            costs[i] = sc.nextInt();
        }

        System.out.print("Enter budget: ");
        long budget = sc.nextLong();

        int[] result = longestStreak(costs, budget);

        System.out.println(
                "(" + result[0] + ", " + result[1] + ")"
        );

        sc.close();
    }
}