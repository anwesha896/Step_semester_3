
import java.util.Scanner;

class IntersectionNode {
    int data;
    IntersectionNode next;

    IntersectionNode(int data) {
        this.data = data;
        next = null;
    }
}

public class FindIntersection {

    static int getLength(IntersectionNode head) {

        int count = 0;

        while (head != null) {
            count++;
            head = head.next;
        }

        return count;
    }

    static IntersectionNode findIntersection(
            IntersectionNode a, IntersectionNode b) {

        int lenA = getLength(a);
        int lenB = getLength(b);

        if (lenA > lenB) {
            for (int i = 0; i < lenA - lenB; i++) {
                a = a.next;
            }
        } else {
            for (int i = 0; i < lenB - lenA; i++) {
                b = b.next;
            }
        }

        while (a != null && b != null) {

            if (a == b) {
                return a;
            }

            a = a.next;
            b = b.next;
        }

        return null;
    }

    static void display(IntersectionNode head) {

        while (head != null) {
            System.out.print(head.data + " -> ");
            head = head.next;
        }

        System.out.println("null");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Create the first list
        System.out.println("Enter number of nodes in first list:");
        int n1 = sc.nextInt();

        IntersectionNode a = null;
        IntersectionNode tailA = null;

        for (int i = 0; i < n1; i++) {

            int value = sc.nextInt();
            IntersectionNode newNode = new IntersectionNode(value);

            if (a == null) {
                a = newNode;
                tailA = newNode;
            } else {
                tailA.next = newNode;
                tailA = newNode;
            }
        }

        // Create the second list
        System.out.println("Enter number of nodes in second list:");
        int n2 = sc.nextInt();

        IntersectionNode b = null;
        IntersectionNode tailB = null;

        for (int i = 0; i < n2; i++) {

            int value = sc.nextInt();
            IntersectionNode newNode = new IntersectionNode(value);

            if (b == null) {
                b = newNode;
                tailB = newNode;
            } else {
                tailB.next = newNode;
                tailB = newNode;
            }
        }

        // Find intersection
        IntersectionNode result = findIntersection(a, b);

        if (result != null) {
            System.out.println("Intersection node: " + result.data);
        } else {
            System.out.println("null");
        }

        sc.close();
    }
}
