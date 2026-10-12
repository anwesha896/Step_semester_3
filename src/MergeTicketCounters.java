
import java.util.Scanner;

class TicketNode {
    int data;
    TicketNode next;

    TicketNode(int data) {
        this.data = data;
        this.next = null;
    }
}

public class MergeTicketCounters {

    static TicketNode mergeSorted(TicketNode a, TicketNode b) {
        TicketNode dummy = new TicketNode(0);
        TicketNode tail = dummy;

        while (a != null && b != null) {
            if (a.data <= b.data) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }

            tail = tail.next;
        }

        if (a != null) {
            tail.next = a;
        } else {
            tail.next = b;
        }

        return dummy.next;
    }

    static TicketNode createList(Scanner sc, int size) {
        TicketNode head = null;
        TicketNode tail = null;

        for (int i = 0; i < size; i++) {
            TicketNode newNode = new TicketNode(sc.nextInt());

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

    static void display(TicketNode head) {
        TicketNode current = head;

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

        int m = sc.nextInt();
        TicketNode a = createList(sc, m);

        int n = sc.nextInt();
        TicketNode b = createList(sc, n);

        TicketNode merged = mergeSorted(a, b);

        display(merged);

        sc.close();
    }
}
