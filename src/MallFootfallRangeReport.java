import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MallFootfallRangeReport {

    public static List<Long> footfallReport(
            int[] visitors, int[][] queries) {

        long[] prefix = new long[visitors.length];

        // Build prefix sum
        prefix[0] = visitors[0];

        for (int i = 1; i < visitors.length; i++) {
            prefix[i] = prefix[i - 1] + visitors[i];
        }

        List<Long> results = new ArrayList<>();

        // Answer each query
        for (int[] query : queries) {

            int start = query[0];
            int end = query[1];

            long sum;

            if (start == 0) {
                sum = prefix[end];
            }
            else {
                sum = prefix[end] - prefix[start - 1];
            }

            results.add(sum);
        }

        return results;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of hours: ");
        int n = sc.nextInt();

        int[] visitors = new int[n];

        System.out.println("Enter visitor counts:");
        for (int i = 0; i < n; i++) {
            visitors[i] = sc.nextInt();
        }

        System.out.print("Enter number of queries: ");
        int q = sc.nextInt();

        int[][] queries = new int[q][2];

        System.out.println("Enter queries (start end):");
        for (int i = 0; i < q; i++) {
            queries[i][0] = sc.nextInt();
            queries[i][1] = sc.nextInt();
        }

        List<Long> results =
                footfallReport(visitors, queries);

        System.out.println("Range sums: " + results);

        sc.close();
    }
}