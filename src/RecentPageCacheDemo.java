
import java.util.*;

class CachePageNode {
    int key;
    String page;
    CachePageNode prev, next;

    CachePageNode(int key, String page) {
        this.key = key;
        this.page = page;
    }
}

class RecentCacheEngine {
    private int capacity;
    private HashMap<Integer, CachePageNode> map;
    private CachePageNode head, tail;

    private Integer lastEvicted;
    private boolean updated;

    RecentCacheEngine(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();

        head = new CachePageNode(0, "");
        tail = new CachePageNode(0, "");

        head.next = tail;
        tail.prev = head;
    }

    // Remove a node from its current position
    private void remove(CachePageNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Insert a node immediately after head
    private void addFirst(CachePageNode node) {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    // Move an existing node to the front
    private void moveToFront(CachePageNode node) {
        remove(node);
        addFirst(node);
    }

    // Get a page using its key
    String get(int key) {
        CachePageNode node = map.get(key);

        if (node == null) {
            return "-1";
        }

        moveToFront(node);
        return node.page;
    }

    // Insert or update a page
    void put(int key, String page) {
        lastEvicted = null;
        updated = false;

        if (map.containsKey(key)) {
            CachePageNode node = map.get(key);
            node.page = page;
            moveToFront(node);
            updated = true;
            return;
        }

        if (map.size() == capacity) {
            CachePageNode lru = tail.prev;

            lastEvicted = lru.key;
            remove(lru);
            map.remove(lru.key);
        }

        CachePageNode node = new CachePageNode(key, page);
        addFirst(node);
        map.put(key, node);
    }

    Integer getLastEvicted() {
        return lastEvicted;
    }

    boolean wasUpdated() {
        return updated;
    }

    void display() {
        System.out.print("Cache (most recent first): ");

        CachePageNode current = head.next;

        while (current != tail) {
            System.out.print(current.key + "=" + current.page);

            current = current.next;

            if (current != tail) {
                System.out.print(", ");
            }
        }

        System.out.println();
    }
}

public class RecentPageCacheDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int capacity = sc.nextInt();
        int n = sc.nextInt();

        RecentCacheEngine cache =
                new RecentCacheEngine(capacity);

        for (int i = 0; i < n; i++) {
            String operation = sc.next();

            if (operation.equalsIgnoreCase("put")) {
                int key = sc.nextInt();
                String page = sc.next();

                cache.put(key, page);

                System.out.print("put(" + key + "," + page + ")");

                if (cache.wasUpdated()) {
                    System.out.println(" updated");
                } else if (cache.getLastEvicted() != null) {
                    System.out.println(" evicted "
                            + cache.getLastEvicted());
                } else {
                    System.out.println();
                }

            } else if (operation.equalsIgnoreCase("get")) {
                int key = sc.nextInt();
                String result = cache.get(key);

                System.out.println("get(" + key + ") -> " + result);
            }
        }

        cache.display();

        sc.close();
    }
}
