import java.util.Scanner;

public class MaximumFixedWindowSales {

    public static int maxSumSubarray(int[] sales, int k) {

        int windowSum = 0;

        // Calculate sum of first window
        for (int i = 0; i < k; i++) {
            windowSum += sales[i];
        }

        int maxSum = windowSum;

        // Slide the window
        for (int i = k; i < sales.length; i++) {

            windowSum = windowSum - sales[i - k] + sales[i];

            if (windowSum > maxSum) {
                maxSum = windowSum;
            }
        }

        return maxSum;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of days: ");
        int n = sc.nextInt();

        int[] sales = new int[n];

        System.out.println("Enter sales figures:");
        for (int i = 0; i < n; i++) {
            sales[i] = sc.nextInt();
        }

        System.out.print("Enter k: ");
        int k = sc.nextInt();

        if (k < 1 || k > n) {
            System.out.println("Invalid value of k");
        }
        else {
            System.out.println(
                    "Maximum sum = " + maxSumSubarray(sales, k)
            );
        }

        sc.close();
    }
}