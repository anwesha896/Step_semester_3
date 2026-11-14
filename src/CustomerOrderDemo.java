
import java.util.Scanner;

class CustomerOrder {
    private ProductLine[] items = new ProductLine[100];
    private int size = 0;

    static class ProductLine {
        String productName;
        int quantity;
        double unitPrice;

        ProductLine(String name, int quantity, double price) {
            productName = name;
            this.quantity = quantity;
            unitPrice = price;
        }

        double total() {
            return quantity * unitPrice;
        }
    }

    class OrderCursor {
        int index = 0;

        boolean hasNext() {
            return index < size;
        }

        ProductLine next() {
            if (hasNext())
                return items[index++];
            return null;
        }

        void reset() {
            index = 0;
        }
    }

    void addItem(String name, int quantity, double price) {
        items[size++] = new ProductLine(name, quantity, price);
    }

    OrderCursor getCursor() {
        return new OrderCursor();
    }
}

public class CustomerOrderDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CustomerOrder order = new CustomerOrder();

        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int quantity = sc.nextInt();
            double price = sc.nextDouble();
            order.addItem(name, quantity, price);
        }

        CustomerOrder.OrderCursor cursor = order.getCursor();
        double total = 0;

        while (cursor.hasNext()) {
            CustomerOrder.ProductLine item = cursor.next();
            double amount = item.total();

            System.out.println(item.productName + " " +
                    (amount == (long) amount ? (long) amount : amount));

            total += amount;
        }

        System.out.println("Total " +
                (total == (long) total ? (long) total : total));

        System.out.println("cursor: inner (reads the order's array)");
        System.out.println("line item: static nested (needs no order)");

        sc.close();
    }
}
