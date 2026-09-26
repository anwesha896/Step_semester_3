import java.util.ArrayList;
import java.util.List;

// Payment abstraction
interface IPaymentMethod {
    boolean pay(double amount);
    String getPaymentMethodName();
}

// Credit Card payment
class CreditCardPayment implements IPaymentMethod {

    public boolean pay(double amount) {
        System.out.println("Payment via Credit Card successful.");
        return true;
    }

    public String getPaymentMethodName() {
        return "Credit Card";
    }
}

// Digital Wallet payment
class DigitalWalletPayment implements IPaymentMethod {

    public boolean pay(double amount) {
        System.out.println("Payment via Digital Wallet failed.");
        return false;
    }

    public String getPaymentMethodName() {
        return "Digital Wallet";
    }
}

// Cash on Delivery payment
class CashOnDeliveryPayment implements IPaymentMethod {

    public boolean pay(double amount) {
        System.out.println("Payment via Cash on Delivery successful.");
        return true;
    }

    public String getPaymentMethodName() {
        return "Cash on Delivery";
    }
}

// Food item
class FoodItem {
    private String name;
    private double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

// LineItem represents one food item and its quantity
class LineItem {
    private FoodItem foodItem;
    private int quantity;

    public LineItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return foodItem.getPrice() * quantity;
    }

    public String getDescription() {
        return foodItem.getName() + " (Qty " + quantity + ")";
    }
}

// Restaurant
class Restaurant {
    private String name;
    private List<FoodItem> menu = new ArrayList<>();

    public Restaurant(String name) {
        this.name = name;
    }

    public void addFoodItem(FoodItem foodItem) {
        menu.add(foodItem);
    }

    public String getName() {
        return name;
    }
}

// Customer
class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void notifyCustomer(String message) {
        System.out.println("Notification: " + message);
    }
}

// Order
class Order {
    private int orderId;
    private Customer customer;
    private Restaurant restaurant;
    private List<LineItem> items;
    private String status;

    public Order(int orderId, Customer customer, Restaurant restaurant) {
        this.orderId = orderId;
        this.customer = customer;
        this.restaurant = restaurant;
        this.items = new ArrayList<>();
        this.status = "Created";

        System.out.println("Order created.");
    }

    public void addItem(FoodItem foodItem, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }

        LineItem item = new LineItem(foodItem, quantity);
        items.add(item);

        System.out.println("Added " + item.getDescription() + ".");
    }

    public void placeOrder() {
        if (items.isEmpty()) {
            System.out.println(
                    "Cannot place order: Order must contain at least one item."
            );
            return;
        }

        status = "Pending Payment";
        System.out.println("Order placed successfully.");
        notifyCustomer("Order #" + orderId + " placed.");
    }

    public void processPayment(IPaymentMethod paymentMethod) {
        if (items.isEmpty()) {
            System.out.println("Cannot process payment for an empty order.");
            return;
        }

        if (!status.equals("Pending Payment")) {
            System.out.println("Payment cannot be processed for this order.");
            return;
        }

        double totalAmount = calculateTotal();

        boolean success = paymentMethod.pay(totalAmount);

        if (success) {
            status = "Paid";
            System.out.println("Order status: Paid.");
            notifyCustomer(
                    "Order #" + orderId + " placed and paid."
            );
        } else {
            status = "Pending Payment";
            System.out.println("Order status: Pending Payment.");
            notifyCustomer(
                    "Order #" + orderId + " placed, awaiting payment."
            );
        }
    }

    private double calculateTotal() {
        double total = 0;

        for (LineItem item : items) {
            total += item.getTotalPrice();
        }

        return total;
    }

    private void notifyCustomer(String message) {
        customer.notifyCustomer(message);
    }
}

// Main class
public class FoodOrderSystem {

    public static void main(String[] args) {

        Customer customer = new Customer("Anwesha");

        Restaurant restaurant =
                new Restaurant("Food Palace");

        FoodItem pizza =
                new FoodItem("Pizza", 250);

        FoodItem soda =
                new FoodItem("Soda", 50);

        FoodItem burger =
                new FoodItem("Burger", 180);

        restaurant.addFoodItem(pizza);
        restaurant.addFoodItem(soda);
        restaurant.addFoodItem(burger);

        // ---------------- ORDER #123 ----------------

        Order order1 =
                new Order(123, customer, restaurant);

        order1.addItem(pizza, 2);
        order1.addItem(soda, 1);

        // Payment attempt after adding items
        order1.placeOrder();

        IPaymentMethod creditCard =
                new CreditCardPayment();

        order1.processPayment(creditCard);

        // ---------------- EMPTY ORDER TEST ----------------

        Order emptyOrder =
                new Order(125, customer, restaurant);

        emptyOrder.placeOrder();

        // ---------------- ORDER #124 ----------------

        Order order2 =
                new Order(124, customer, restaurant);

        order2.addItem(burger, 1);

        order2.placeOrder();

        IPaymentMethod digitalWallet =
                new DigitalWalletPayment();

        order2.processPayment(digitalWallet);
    }
}