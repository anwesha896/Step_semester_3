
import java.util.Scanner;

class ReorderNode {
    int data;
    ReorderNode next;

    ReorderNode(int data) {
        this.data = data;
        next = null;
    }
}

public class ReorderLinkedList {

    // Reverse the second half
    static ReorderNode reverse(ReorderNode head) {

        ReorderNode prev = null;
        ReorderNode current = head;

        while (current != null) {

            ReorderNode nextNode = current.next;

            current.next = prev;

            prev = current;
            current = nextNode;
        }

        return prev;
    }

    // Reorder the linked list
    static void reorderList(ReorderNode head) {

        if (head == null || head.next == null) {
            return;
        }

        // Step 1: Find the middle
        ReorderNode slow = head;
        ReorderNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: Split into two halves
        ReorderNode second = slow.next;
        slow.next = null;

        // Reverse the second half
        second = reverse(second);

        // Step 3: Merge alternately
        ReorderNode first = head;

        while (second != null) {

            ReorderNode temp1 = first.next;
            ReorderNode temp2 = second.next;

            first.next = second;
            second.next = temp1;

            first = temp1;
            second = temp2;
        }
    }

    // Display the linked list
    static void display(ReorderNode head) {

        while (head != null) {
            System.out.print(head.data);

            if (head.next != null) {
                System.out.print(" -> ");
            }

            head = head.next;
        }

        System.out.println();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of nodes:");
        int n = sc.nextInt();

        ReorderNode head = null;
        ReorderNode tail = null;

        System.out.println("Enter the elements:");

        for (int i = 0; i < n; i++) {

            int value = sc.nextInt();
            ReorderNode newNode = new ReorderNode(value);

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        reorderList(head);

        System.out.println("Reordered list:");
        display(head);

        sc.close();
    }
}
