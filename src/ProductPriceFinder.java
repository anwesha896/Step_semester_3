import java.util.Scanner;

public class ProductPriceFinder {

    public static int findLowerBound(int[] prices, int low) {
        int left = 0;
        int right = prices.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (prices[mid] >= low) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public static int findUpperBound(int[] prices, int high) {
        int left = 0;
        int right = prices.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (prices[mid] > high) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of products: ");
        int n = sc.nextInt();

        int[] prices = new int[n];

        System.out.println("Enter prices in ascending order:");
        for (int i = 0; i < n; i++) {
            prices[i] = sc.nextInt();
        }

        System.out.print("Enter lower price: ");
        int low = sc.nextInt();

        System.out.print("Enter upper price: ");
        int high = sc.nextInt();

        int lowerBound = findLowerBound(prices, low);
        int upperBound = findUpperBound(prices, high);

        System.out.println("Lower bound index " + lowerBound);
        System.out.println("Upper bound index " + upperBound);
        System.out.println("Count " + (upperBound - lowerBound));

        sc.close();
    }
}
