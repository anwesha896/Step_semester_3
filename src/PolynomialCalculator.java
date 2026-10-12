
import java.util.Scanner;

class PolynomialTermNode {
    int coefficient;
    int exponent;
    PolynomialTermNode next;

    PolynomialTermNode(int coefficient, int exponent) {
        this.coefficient = coefficient;
        this.exponent = exponent;
        this.next = null;
    }
}

public class PolynomialCalculator {

    static PolynomialTermNode addPolynomials(
            PolynomialTermNode p, PolynomialTermNode q) {

        PolynomialTermNode dummy = new PolynomialTermNode(0, 0);
        PolynomialTermNode tail = dummy;

        while (p != null && q != null) {
            if (p.exponent > q.exponent) {
                tail.next = new PolynomialTermNode(
                        p.coefficient, p.exponent);
                p = p.next;
            } else if (q.exponent > p.exponent) {
                tail.next = new PolynomialTermNode(
                        q.coefficient, q.exponent);
                q = q.next;
            } else {
                int sum = p.coefficient + q.coefficient;

                if (sum != 0) {
                    tail.next = new PolynomialTermNode(
                            sum, p.exponent);
                }

                p = p.next;
                q = q.next;

                if (sum == 0) {
                    continue;
                }
            }

            tail = tail.next;
        }

        while (p != null) {
            tail.next = new PolynomialTermNode(
                    p.coefficient, p.exponent);
            tail = tail.next;
            p = p.next;
        }

        while (q != null) {
            tail.next = new PolynomialTermNode(
                    q.coefficient, q.exponent);
            tail = tail.next;
            q = q.next;
        }

        return dummy.next;
    }

    static void printPolynomial(PolynomialTermNode head) {
        if (head == null) {
            System.out.println("0");
            return;
        }

        PolynomialTermNode current = head;
        boolean first = true;

        while (current != null) {
            int c = current.coefficient;
            int e = current.exponent;

            if (!first) {
                if (c > 0) {
                    System.out.print(" + ");
                } else {
                    System.out.print(" - ");
                }
            } else if (c < 0) {
                System.out.print("-");
            }

            int absC = Math.abs(c);

            if (e == 0 || absC != 1) {
                System.out.print(absC);
            }

            if (e > 0) {
                System.out.print("x");

                if (e > 1) {
                    System.out.print("^" + e);
                }
            }

            first = false;
            current = current.next;
        }

        System.out.println();
    }

    static PolynomialTermNode createPolynomial(
            Scanner sc, int terms) {

        PolynomialTermNode head = null;
        PolynomialTermNode tail = null;

        for (int i = 0; i < terms; i++) {
            int coefficient = sc.nextInt();
            int exponent = sc.nextInt();

            PolynomialTermNode node =
                    new PolynomialTermNode(coefficient, exponent);

            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
        }

        return head;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int m = sc.nextInt();
        PolynomialTermNode p = createPolynomial(sc, m);

        int n = sc.nextInt();
        PolynomialTermNode q = createPolynomial(sc, n);

        PolynomialTermNode result = addPolynomials(p, q);

        printPolynomial(result);

        sc.close();
    }
}
