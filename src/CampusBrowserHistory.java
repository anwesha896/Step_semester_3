
import java.util.Scanner;

class BrowserHistoryNode {
    String url;
    BrowserHistoryNode prev;
    BrowserHistoryNode next;

    BrowserHistoryNode(String url) {
        this.url = url;
        this.prev = null;
        this.next = null;
    }
}

class BrowserHistory {
    BrowserHistoryNode current;

    BrowserHistory(String homepage) {
        current = new BrowserHistoryNode(homepage);
    }

    void visit(String url) {
        BrowserHistoryNode newNode = new BrowserHistoryNode(url);

        current.next = null;
        newNode.prev = current;
        current.next = newNode;

        current = newNode;
    }

    String back(int steps) {
        while (steps > 0 && current.prev != null) {
            current = current.prev;
            steps--;
        }

        return current.url;
    }

    String forward(int steps) {
        while (steps > 0 && current.next != null) {
            current = current.next;
            steps--;
        }

        return current.url;
    }
}

public class CampusBrowserHistory {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String homepage = sc.nextLine();
        BrowserHistory browser = new BrowserHistory(homepage);

        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().split(" ");

            if (parts[0].equals("visit")) {
                browser.visit(parts[1]);
            } else if (parts[0].equals("back")) {
                System.out.println(browser.back(
                        Integer.parseInt(parts[1])));
            } else if (parts[0].equals("forward")) {
                System.out.println(browser.forward(
                        Integer.parseInt(parts[1])));
            }
        }

        sc.close();
    }
}
