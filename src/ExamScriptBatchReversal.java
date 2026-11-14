
import java.util.Scanner;

class ExamScriptNode {
    int data;
    ExamScriptNode next;

    ExamScriptNode(int data) {
        this.data = data;
        next = null;
    }
}

class ExamScriptBatchProcessor {

    ExamScriptNode reverseInGroups(ExamScriptNode head, int k) {

        ExamScriptNode dummy = new ExamScriptNode(0);
        dummy.next = head;

        ExamScriptNode prevGroup = dummy;

        while (true) {

            // Check whether k nodes are available
            ExamScriptNode kth = prevGroup;

            for (int i = 0; i < k && kth != null; i++) {
                kth = kth.next;
            }

            if (kth == null) {
                break;
            }

            ExamScriptNode groupNext = kth.next;

            // Reverse the current group
            ExamScriptNode prev = groupNext;
            ExamScriptNode curr = prevGroup.next;

            while (curr != groupNext) {
                ExamScriptNode nextNode = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nextNode;
            }

            // Connect the reversed group
            ExamScriptNode oldGroupHead = prevGroup.next;
            prevGroup.next = kth;
            prevGroup = oldGroupHead;
        }

        return dummy.next;
    }
}

public class ExamScriptBatchReversal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        ExamScriptNode head = null;
        ExamScriptNode tail = null;

        for (int i = 0; i < n; i++) {
            ExamScriptNode node =
                    new ExamScriptNode(sc.nextInt());

            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
        }

        int k = sc.nextInt();

        ExamScriptBatchProcessor processor =
                new ExamScriptBatchProcessor();

        head = processor.reverseInGroups(head, k);

        ExamScriptNode temp = head;

        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }

        System.out.println("null");

        sc.close();
    }
}
