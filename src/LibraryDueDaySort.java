import java.util.Scanner;

public class LibraryDueDaySort {

    static class Node {
        String title;
        int dueDay;
        Node next;

        Node(String title, int dueDay) {
            this.title = title;
            this.dueDay = dueDay;
            this.next = null;
        }
    }

    // Sort the linked list using merge sort
    static Node sortByDueDay(Node head) {

        if (head == null || head.next == null) {
            return head;
        }

        // Find the middle
        Node slow = head;
        Node fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Split the list into two halves
        Node right = slow.next;
        slow.next = null;

        // Sort both halves
        Node leftSorted = sortByDueDay(head);
        Node rightSorted = sortByDueDay(right);

        // Merge the sorted halves
        return merge(leftSorted, rightSorted);
    }

    static Node merge(Node left, Node right) {

        Node dummy = new Node("", 0);
        Node tail = dummy;

        while (left != null && right != null) {

            // Take left node on ties to maintain stability
            if (left.dueDay <= right.dueDay) {
                tail.next = left;
                left = left.next;
            } else {
                tail.next = right;
                right = right.next;
            }

            tail = tail.next;
        }

        if (left != null) {
            tail.next = left;
        } else {
            tail.next = right;
        }

        return dummy.next;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        Node head = null;
        Node tail = null;

        for (int i = 0; i < n; i++) {

            String item = sc.next();
            int pos = item.lastIndexOf(':');

            String title = item.substring(0, pos);
            int day = Integer.parseInt(item.substring(pos + 1));

            Node newNode = new Node(title, day);

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        head = sortByDueDay(head);

        Node temp = head;

        while (temp != null) {
            System.out.print(temp.title + ":" + temp.dueDay + " -> ");
            temp = temp.next;
        }

        System.out.println("null");

        sc.close();
    }
}