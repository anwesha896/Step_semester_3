import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NetBalancePeriodCounter {

    public static long countPeriods(int[] transactions, long k) {
        Map<Long, Integer> prefixCount = new HashMap<>();

        prefixCount.put(0L, 1);

        long prefixSum = 0;
        long count = 0;

        for (int transaction : transactions) {
            prefixSum += transaction;

            long requiredPrefix = prefixSum - k;

            if (prefixCount.containsKey(requiredPrefix)) {
                count += prefixCount.get(requiredPrefix);
            }

            prefixCount.put(prefixSum,
                    prefixCount.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of days: ");
        int n = sc.nextInt();

        int[] transactions = new int[n];

        System.out.println("Enter daily transactions:");
        for (int i = 0; i < n; i++) {
            transactions[i] = sc.nextInt();
        }

        System.out.print("Enter target k: ");
        long k = sc.nextLong();

        System.out.println("Number of periods = "
                + countPeriods(transactions, k));

        sc.close();
    }
}
