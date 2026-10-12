
import java.util.Scanner;

class TrainCodeNode {
    int data;
    TrainCodeNode next;

    TrainCodeNode(int data) {
        this.data = data;
        this.next = null;
    }
}

public class PalindromeTrainCode {

    static TrainCodeNode reverse(TrainCodeNode head) {
        TrainCodeNode prev = null;
        TrainCodeNode current = head;

        while (current != null) {
            TrainCodeNode nextNode = current.next;
            current.next = prev;
            prev = current;
            current = nextNode;
        }

        return prev;
    }

    static boolean isPalindrome(TrainCodeNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        TrainCodeNode slow = head;
        TrainCodeNode fast = head;

        // Find the middle of the list
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse the second half
        TrainCodeNode secondHalf = reverse(slow.next);
        TrainCodeNode first = head;
        TrainCodeNode second = secondHalf;

        boolean palindrome = true;

        // Compare both halves
        while (second != null) {
            if (first.data != second.data) {
                palindrome = false;
                break;
            }

            first = first.next;
            second = second.next;
        }

        // Restore the original list
        slow.next = reverse(secondHalf);

        return palindrome;
    }

    static void display(TrainCodeNode head) {
        TrainCodeNode current = head;

        while (current != null) {
            System.out.print(current.data);

            if (current.next != null) {
                System.out.print(" -> ");
            }

            current = current.next;
        }

        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        TrainCodeNode head = null;
        TrainCodeNode tail = null;

        for (int i = 0; i < n; i++) {
            TrainCodeNode newNode =
                    new TrainCodeNode(sc.nextInt());

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        System.out.println(isPalindrome(head));
        System.out.print("List after check: ");
        display(head);

        sc.close();
    }
}
