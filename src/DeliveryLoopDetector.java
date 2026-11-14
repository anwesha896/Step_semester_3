
import java.util.Scanner;

class DeliveryStopNode {
    int data;
    DeliveryStopNode next;

    DeliveryStopNode(int data) {
        this.data = data;
        next = null;
    }
}

class DeliveryRouteLoopDetector {

    String detectLoop(DeliveryStopNode head) {
        DeliveryStopNode slow = head;
        DeliveryStopNode fast = head;

        // Step 1: Detect loop
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {

                // Step 2: Find loop length
                int length = 1;
                DeliveryStopNode temp = slow.next;

                while (temp != slow) {
                    length++;
                    temp = temp.next;
                }

                // Step 3: Find loop starting node
                slow = head;

                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }

                return "Loop: yes, starts at stop "
                        + slow.data + ", length " + length;
            }
        }

        return "Loop: no";
    }
}

public class DeliveryLoopDetector {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        DeliveryStopNode head = null;
        DeliveryStopNode tail = null;

        DeliveryStopNode[] nodes = new DeliveryStopNode[n];

        for (int i = 0; i < n; i++) {
            int value = sc.nextInt();

            nodes[i] = new DeliveryStopNode(value);

            if (head == null) {
                head = nodes[i];
                tail = nodes[i];
            } else {
                tail.next = nodes[i];
                tail = nodes[i];
            }
        }

        int pos = sc.nextInt();

        // Connect last node to the given position
        if (pos >= 0) {
            tail.next = nodes[pos];
        }

        DeliveryRouteLoopDetector detector =
                new DeliveryRouteLoopDetector();

        System.out.println(detector.detectLoop(head));

        sc.close();
    }
}
