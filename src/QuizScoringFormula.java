
import java.util.Scanner;
import java.util.Stack;

public class QuizScoringFormula {

    // Return operator precedence
    static int precedence(char op) {

        if (op == '*' || op == '/') {
            return 2;
        }

        if (op == '+' || op == '-') {
            return 1;
        }

        return 0;
    }

    // Perform calculation
    static int calculate(int a, int b, char op) {

        if (op == '+') {
            return a + b;
        }

        if (op == '-') {
            return a - b;
        }

        if (op == '*') {
            return a * b;
        }

        return a / b;
    }

    // Evaluate expression
    static int evaluate(String expr) {

        Stack<Integer> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {

            char ch = expr.charAt(i);

            // Ignore spaces
            if (ch == ' ') {
                continue;
            }

            // If digit, form complete number
            if (Character.isDigit(ch)) {

                int num = 0;

                while (i < expr.length() &&
                        Character.isDigit(expr.charAt(i))) {

                    num = num * 10 + (expr.charAt(i) - '0');
                    i++;
                }

                i--;
                numbers.push(num);
            }

            // Opening bracket
            else if (ch == '(') {
                operators.push(ch);
            }

            // Closing bracket
            else if (ch == ')') {

                while (!operators.isEmpty() &&
                        operators.peek() != '(') {

                    int b = numbers.pop();
                    int a = numbers.pop();

                    char op = operators.pop();

                    numbers.push(calculate(a, b, op));
                }

                operators.pop();
            }

            // If operator
            else {

                while (!operators.isEmpty() &&
                        operators.peek() != '(' &&
                        precedence(operators.peek()) >= precedence(ch)) {

                    int b = numbers.pop();
                    int a = numbers.pop();

                    char op = operators.pop();

                    numbers.push(calculate(a, b, op));
                }

                operators.push(ch);
            }
        }

        // Perform remaining operations
        while (!operators.isEmpty()) {

            int b = numbers.pop();
            int a = numbers.pop();

            char op = operators.pop();

            numbers.push(calculate(a, b, op));
        }

        return numbers.pop();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter scoring expression:");
        String expr = sc.nextLine();

        int result = evaluate(expr);

        System.out.println("Result: " + result);

        sc.close();
    }
}
