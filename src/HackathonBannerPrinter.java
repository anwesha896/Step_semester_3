
import java.util.Scanner;
import java.util.Stack;

public class HackathonBannerPrinter {

    static String expandBanner(String code) {

        Stack<Integer> countStack = new Stack<>();
        Stack<String> stringStack = new Stack<>();

        StringBuilder current = new StringBuilder();

        int count = 0;

        for (int i = 0; i < code.length(); i++) {

            char ch = code.charAt(i);

            // If character is a digit
            if (Character.isDigit(ch)) {
                count = count * 10 + (ch - '0');
            }

            // If opening bracket
            else if (ch == '[') {

                countStack.push(count);
                stringStack.push(current.toString());

                count = 0;
                current = new StringBuilder();
            }

            // If closing bracket
            else if (ch == ']') {

                int repeat = countStack.pop();
                String previous = stringStack.pop();

                StringBuilder temp = new StringBuilder();

                for (int j = 0; j < repeat; j++) {
                    temp.append(current);
                }

                current = new StringBuilder(previous);
                current.append(temp);
            }

            // If character is a letter
            else {
                current.append(ch);
            }
        }

        return current.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter compressed banner code:");
        String code = sc.nextLine();

        String result = expandBanner(code);

        System.out.println("Expanded banner:");
        System.out.println(result);

        sc.close();
    }
}
