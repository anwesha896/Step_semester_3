
import java.util.Scanner;

class NthNode {
    int data;
    NthNode next;

    NthNode(int data) {
        this.data = data;
        this.next = null;
    }
}

public class RemoveNthNodeFromEnd {

    static NthNode removeNthFromEnd(NthNode head, int n) {

        NthNode dummy = new NthNode(0);
        dummy.next = head;

        NthNode fast = dummy;
        NthNode slow = dummy;

        // Move fast pointer n steps ahead
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        // Move both pointers together
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // Remove the required node
        slow.next = slow.next.next;

        return dummy.next;
    }

    static void display(NthNode head) {
        NthNode current = head;

        if (head == null) {
            System.out.println("Empty list");
            return;
        }

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

        int size = sc.nextInt();

        NthNode head = null;
        NthNode tail = null;

        for (int i = 0; i < size; i++) {
            NthNode newNode = new NthNode(sc.nextInt());

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        int n = sc.nextInt();

        head = removeNthFromEnd(head, n);

        display(head);

        sc.close();
    }
}
