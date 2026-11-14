
import java.util.Scanner;

class TokenMirrorNode {
    int data;
    TokenMirrorNode next;

    TokenMirrorNode(int data) {
        this.data = data;
        this.next = null;
    }
}

class TokenMirrorChecker {

    TokenMirrorNode reverse(TokenMirrorNode head) {
        TokenMirrorNode prev = null;
        TokenMirrorNode curr = head;

        while (curr != null) {
            TokenMirrorNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }

        return prev;
    }

    boolean isPalindrome(TokenMirrorNode head) {

        if (head == null || head.next == null)
            return true;

        // Find the end of the first half
        TokenMirrorNode slow = head;
        TokenMirrorNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse the second half
        TokenMirrorNode secondHalf = reverse(slow.next);
        slow.next = secondHalf;

        // Compare both halves
        TokenMirrorNode first = head;
        TokenMirrorNode second = secondHalf;

        boolean result = true;

        while (second != null) {
            if (first.data != second.data) {
                result = false;
                break;
            }

            first = first.next;
            second = second.next;
        }

        // Restore the original list
        slow.next = reverse(secondHalf);

        return result;
    }
}

public class TokenMirrorCheckDemo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        TokenMirrorNode head = null;
        TokenMirrorNode tail = null;

        for (int i = 0; i < n; i++) {
            TokenMirrorNode newNode =
                    new TokenMirrorNode(sc.nextInt());

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        TokenMirrorChecker checker = new TokenMirrorChecker();

        System.out.println("Palindrome: " +
                checker.isPalindrome(head));

        System.out.print("List after check: ");

        TokenMirrorNode temp = head;

        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }

        System.out.println("null");

        sc.close();
    }
}
