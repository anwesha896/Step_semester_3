public class BookInventoryManager {
    private int copiesTotal;
    private int copiesAvailable;
    // Constructor
    public BookInventoryManager(int copiesTotal) {
        if (copiesTotal <= 0) {
            throw new IllegalArgumentException(
                    "Copies total must be positive"
            );
        }
        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }
    // Check out a book
    public void checkOut() {
        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }
    // Check in a book
    public void checkIn() {
        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }
    // Getter
    public int getCopiesAvailable() {
        return copiesAvailable;
    }
    public static void main(String[] args) {
        // Test 1: Invalid inventory
        try {
            BookInventoryManager b1 = new BookInventoryManager(0);
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
        // Test 2: Four checkouts from 3 copies
        BookInventoryManager b = new BookInventoryManager(3);
        b.checkOut();
        b.checkOut();
        b.checkOut();
        b.checkOut();
        System.out.println(b.getCopiesAvailable());
        // Test 3: Four check-ins
        b.checkIn();
        b.checkIn();
        b.checkIn();
        b.checkIn();
        System.out.println(b.getCopiesAvailable());
    }
}
