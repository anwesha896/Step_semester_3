
import java.util.Scanner;

class OrderNode {
    int data;
    OrderNode next;

    OrderNode(int data) {
        this.data = data;
        this.next = null;
    }
}

public class CancelKthLastOrder {

    static OrderNode removeKthFromEnd(OrderNode head, int k) {
        OrderNode dummy = new OrderNode(0);
        dummy.next = head;

        OrderNode lead = dummy;
        OrderNode trail = dummy;

        for (int i = 0; i <= k; i++) {
            lead = lead.next;
        }

        while (lead != null) {
            lead = lead.next;
            trail = trail.next;
        }

        trail.next = trail.next.next;

        return dummy.next;
    }

    static void display(OrderNode head) {
        OrderNode current = head;

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

        OrderNode head = null;
        OrderNode tail = null;

        for (int i = 0; i < n; i++) {
            int value = sc.nextInt();
            OrderNode newNode = new OrderNode(value);

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        int k = sc.nextInt();

        head = removeKthFromEnd(head, k);

        display(head);

        sc.close();
    }
}
