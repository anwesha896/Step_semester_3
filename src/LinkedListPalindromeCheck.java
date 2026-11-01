
import java.util.Scanner;

class PalindromeNode {
    int data;
    PalindromeNode next;

    PalindromeNode(int data) {
        this.data = data;
        this.next = null;
    }
}

public class LinkedListPalindromeCheck {

    static PalindromeNode reverse(PalindromeNode head) {
        PalindromeNode prev = null;
        PalindromeNode current = head;

        while (current != null) {
            PalindromeNode nextNode = current.next;
            current.next = prev;
            prev = current;
            current = nextNode;
        }

        return prev;
    }

    static boolean isPalindrome(PalindromeNode head) {

        if (head == null || head.next == null) {
            return true;
        }

        PalindromeNode slow = head;
        PalindromeNode fast = head;

        // Find the middle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // For odd length, skip the middle node
        if (fast != null) {
            slow = slow.next;
        }

        // Reverse the second half
        PalindromeNode secondHalf = reverse(slow);

        PalindromeNode first = head;
        PalindromeNode second = secondHalf;

        boolean result = true;

        // Compare both halves
        while (second != null) {
            if (first.data != second.data) {
                result = false;
                break;
            }

            first = first.next;
            second = second.next;
        }

        // Restore the original list
        reverse(secondHalf);

        return result;
    }

    static void display(PalindromeNode head) {
        PalindromeNode current = head;

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

        PalindromeNode head = null;
        PalindromeNode tail = null;

        for (int i = 0; i < n; i++) {
            PalindromeNode newNode =
                    new PalindromeNode(sc.nextInt());

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        System.out.println(isPalindrome(head));

        System.out.print("Original list: ");
        display(head);

        sc.close();
    }
}
