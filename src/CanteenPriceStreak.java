
import java.util.Scanner;
import java.util.Stack;

public class CanteenPriceStreak {

    static int[] priceStreak(int[] price) {

        int n = price.length;

        int[] result = new int[n];

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {

            while (!stack.isEmpty() &&
                    price[stack.peek()] <= price[i]) {

                stack.pop();
            }

            if (stack.isEmpty()) {
                result[i] = i + 1;
            } else {
                result[i] = i - stack.peek();
            }

            stack.push(i);
        }

        return result;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of days:");
        int n = sc.nextInt();

        int[] price = new int[n];

        System.out.println("Enter daily prices:");

        for (int i = 0; i < n; i++) {
            price[i] = sc.nextInt();
        }

        int[] result = priceStreak(price);

        System.out.println("Price streak:");

        for (int i = 0; i < n; i++) {
            System.out.print(result[i] + " ");
        }

        sc.close();
    }
}

