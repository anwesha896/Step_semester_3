class CanteenOrder {
    private String studentName;
    private String dishName;
    private boolean delivered;
    // Parameterized constructor
    public CanteenOrder(String studentName, String dishName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be blank");
        }
        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Dish name cannot be blank");
        }
        this.studentName = studentName;
        this.dishName = dishName;
        this.delivered = false;
    }
    void markDelivered(){
        if (!delivered) {
            delivered = true;
            System.out.println(studentName + "'s order marked as delivered.");
        }
        else {
            System.out.println("Warning: " + studentName + "'s order was already delivered.");
        }
    }
    static void processBatch(String[][] rawOrders) {
        int valid = 0;
        int rejected = 0;
        for (int i = 0; i < rawOrders.length; i++) {
            try {
                CanteenOrder order = new CanteenOrder(rawOrders[i][0], rawOrders[i][1]);
                valid++;
            }
            catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }
}
public class CanteenOrderSystem {
    public static void main(String[] args) {
        String[][] orders = {{"Ravi", "Paneer Butter Masala"}, {"", "Chole Bhature"}, {"Meera", " "},
                {"Divya", "Veg Biryani"}};
        CanteenOrder.processBatch(orders);
        // Testing markDelivered()
        CanteenOrder order = new CanteenOrder("Ravi", "Paneer Butter Masala");
        order.markDelivered();
        order.markDelivered();
    }
}

