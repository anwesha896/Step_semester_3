
import java.util.Scanner;

class BusBay {

    int[] arr;
    int capacity;
    int front;
    int size;

    BusBay(int capacity) {
        this.capacity = capacity;
        arr = new int[capacity];
        front = 0;
        size = 0;
    }

    boolean isEmpty() {
        return size == 0;
    }

    boolean isFull() {
        return size == capacity;
    }

    // Insert bus at front
    boolean insertFront(int bus) {

        if (isFull()) {
            return false;
        }

        front = (front - 1 + capacity) % capacity;

        arr[front] = bus;
        size++;

        return true;
    }

    // Insert bus at rear
    boolean insertLast(int bus) {

        if (isFull()) {
            return false;
        }

        int rear = (front + size) % capacity;

        arr[rear] = bus;
        size++;

        return true;
    }

    // Delete bus from front
    boolean deleteFront() {

        if (isEmpty()) {
            return false;
        }

        front = (front + 1) % capacity;
        size--;

        return true;
    }

    // Delete bus from rear
    boolean deleteLast() {

        if (isEmpty()) {
            return false;
        }

        size--;

        return true;
    }

    // Get front bus
    int getFront() {

        if (isEmpty()) {
            return -1;
        }

        return arr[front];
    }

    // Get rear bus
    int getRear() {

        if (isEmpty()) {
            return -1;
        }

        int rear = (front + size - 1) % capacity;

        return arr[rear];
    }
}

public class BusBayRing {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter capacity:");
        int capacity = sc.nextInt();

        BusBay b = new BusBay(capacity);

        System.out.println("Enter number of operations:");
        int n = sc.nextInt();

        System.out.println("Operations:");
        System.out.println("1. Insert Front");
        System.out.println("2. Insert Last");
        System.out.println("3. Delete Front");
        System.out.println("4. Delete Last");
        System.out.println("5. Get Front");
        System.out.println("6. Get Rear");
        System.out.println("7. Is Empty");
        System.out.println("8. Is Full");

        for (int i = 0; i < n; i++) {

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    int x = sc.nextInt();
                    System.out.println(b.insertFront(x));
                    break;

                case 2:
                    x = sc.nextInt();
                    System.out.println(b.insertLast(x));
                    break;

                case 3:
                    System.out.println(b.deleteFront());
                    break;

                case 4:
                    System.out.println(b.deleteLast());
                    break;

                case 5:
                    System.out.println(b.getFront());
                    break;

                case 6:
                    System.out.println(b.getRear());
                    break;

                case 7:
                    System.out.println(b.isEmpty());
                    break;

                case 8:
                    System.out.println(b.isFull());
                    break;
            }
        }

        sc.close();
    }
}

