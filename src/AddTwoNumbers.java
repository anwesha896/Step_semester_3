
import java.util.Scanner;

class NumberNode {
    int data;
    NumberNode next;

    NumberNode(int data) {
        this.data = data;
        next = null;
    }
}

public class AddTwoNumbers {

    static NumberNode addNumbers(NumberNode a, NumberNode b) {

        NumberNode dummy = new NumberNode(0);
        NumberNode tail = dummy;

        int carry = 0;

        while (a != null || b != null || carry != 0) {

            int sum = carry;

            if (a != null) {
                sum = sum + a.data;
                a = a.next;
            }

            if (b != null) {
                sum = sum + b.data;
                b = b.next;
            }

            carry = sum / 10;

            NumberNode newNode = new NumberNode(sum % 10);

            tail.next = newNode;
            tail = newNode;
        }

        return dummy.next;
    }

    static void display(NumberNode head) {

        while (head != null) {
            System.out.print(head.data);

            if (head.next != null) {
                System.out.print(" -> ");
            }

            head = head.next;
        }

        System.out.println();
    }

    static NumberNode createList(Scanner sc, int n) {

        NumberNode head = null;
        NumberNode tail = null;

        for (int i = 0; i < n; i++) {

            int digit = sc.nextInt();
            NumberNode newNode = new NumberNode(digit);

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        return head;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of digits in first list:");
        int n1 = sc.nextInt();

        System.out.println("Enter digits of first list:");
        NumberNode a = createList(sc, n1);

        System.out.println("Enter number of digits in second list:");
        int n2 = sc.nextInt();

        System.out.println("Enter digits of second list:");
        NumberNode b = createList(sc, n2);

        NumberNode result = addNumbers(a, b);

        System.out.println("Sum:");
        display(result);

        sc.close();
    }
}

