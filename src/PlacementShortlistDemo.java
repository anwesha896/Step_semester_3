
import java.util.Scanner;

class CandidateRecord {
    String name;
    int[] scores = new int[6];
    int total;
    int bestGain;
    int startTest;
    int endTest;

    CandidateRecord(String name, int[] scores) {
        this.name = name;

        for (int i = 0; i < 6; i++) {
            this.scores[i] = scores[i];
            total += scores[i];
        }

        findBestClimb();
    }

    void findBestClimb() {
        int currentSum = 0;
        int currentStart = 0;

        bestGain = Integer.MIN_VALUE;

        for (int i = 0; i < 5; i++) {
            int change = scores[i + 1] - scores[i];

            if (currentSum < 0) {
                currentSum = 0;
                currentStart = i;
            }

            currentSum += change;

            if (currentSum > bestGain) {
                bestGain = currentSum;
                startTest = currentStart + 1;
                endTest = i + 2;
            }
        }
    }
}

class PlacementShortlistEngine {

    // Stable merge sort in descending order
    void mergeSort(CandidateRecord[] a, int left, int right) {
        if (left >= right)
            return;

        int mid = (left + right) / 2;

        mergeSort(a, left, mid);
        mergeSort(a, mid + 1, right);

        merge(a, left, mid, right);
    }

    void merge(CandidateRecord[] a, int left, int mid, int right) {
        CandidateRecord[] temp = new CandidateRecord[right - left + 1];

        int i = left;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= right) {
            if (a[i].total >= a[j].total) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }

        while (i <= mid)
            temp[k++] = a[i++];

        while (j <= right)
            temp[k++] = a[j++];

        for (i = 0; i < temp.length; i++)
            a[left + i] = temp[i];
    }

    // Count candidates with total >= cutoff
    int cutoff(CandidateRecord[] a, int n, int value) {
        int low = 0;
        int high = n - 1;
        int answer = n;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (a[mid].total >= value) {
                low = mid + 1;
            } else {
                answer = mid;
                high = mid - 1;
            }
        }

        return answer;
    }

    // Find two candidates with the required total
    void pair(CandidateRecord[] a, int n, int target) {
        int left = 0;
        int right = n - 1;

        while (left < right) {
            int sum = a[left].total + a[right].total;

            if (sum == target) {
                System.out.println("PAIR " + target + ": "
                        + a[left].name + " + " + a[right].name);
                return;
            } else if (sum > target) {
                left++;
            } else {
                right--;
            }
        }

        System.out.println("PAIR " + target + ": no pair");
    }
}

public class PlacementShortlistDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        CandidateRecord[] candidates = new CandidateRecord[n];

        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int[] scores = new int[6];

            for (int j = 0; j < 6; j++) {
                scores[j] = sc.nextInt();
            }

            candidates[i] = new CandidateRecord(name, scores);
        }

        PlacementShortlistEngine engine =
                new PlacementShortlistEngine();

        engine.mergeSort(candidates, 0, n - 1);

        System.out.println("Shortlist:");

        for (int i = 0; i < n; i++) {
            CandidateRecord c = candidates[i];

            System.out.println((i + 1) + ". " + c.name + " "
                    + c.total + " | best climb "
                    + (c.bestGain >= 0 ? "+" : "") + c.bestGain
                    + " (test " + c.startTest + " to " + c.endTest + ")");
        }

        while (sc.hasNext()) {
            String command = sc.next();
            int value = sc.nextInt();

            if (command.equalsIgnoreCase("CUTOFF")) {
                int count = engine.cutoff(candidates, n, value);
                System.out.println("CUTOFF " + value + ": "
                        + count + " candidates");
            } else if (command.equalsIgnoreCase("PAIR")) {
                engine.pair(candidates, n, value);
            }
        }

        sc.close();
    }
}

