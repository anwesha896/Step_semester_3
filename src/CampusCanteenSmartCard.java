import java.util.ArrayList;
import java.util.List;

interface CanteenPricingPlan {
    double calculatePrice(double originalPrice);
    String getPlanName();
}

class CanteenDayScholarPlan implements CanteenPricingPlan {
    public double calculatePrice(double originalPrice) {
        return originalPrice;
    }

    public String getPlanName() {
        return "Day Scholar";
    }
}

class CanteenHostellerPlan implements CanteenPricingPlan {
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.90;
    }

    public String getPlanName() {
        return "Hosteller";
    }
}

class CanteenStaffPlan implements CanteenPricingPlan {
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.80;
    }

    public String getPlanName() {
        return "Staff";
    }
}

class CanteenTransaction {
    private double amount;

    public CanteenTransaction(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

class CanteenPurchase {
    private String itemName;
    private double chargedAmount;
    private boolean refunded;

    public CanteenPurchase(String itemName, double chargedAmount) {
        this.itemName = itemName;
        this.chargedAmount = chargedAmount;
        this.refunded = false;
    }

    public String getItemName() {
        return itemName;
    }

    public double getChargedAmount() {
        return chargedAmount;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void markRefunded() {
        refunded = true;
    }
}

class CanteenSmartCard {
    private String cardId;
    private double balance;
    private List<CanteenTransaction> transactions;
    private List<CanteenPurchase> purchases;
    private CanteenPricingPlan pricingPlan;
    private boolean blocked;

    public CanteenSmartCard(String cardId, CanteenPricingPlan pricingPlan) {
        this.cardId = cardId;
        this.pricingPlan = pricingPlan;
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.purchases = new ArrayList<>();
        this.blocked = false;
    }

    public void topUp(double amount) {
        if (blocked) {
            System.out.println("Top-up rejected: Card is blocked.");
            return;
        }

        if (amount < 100) {
            System.out.println("Top-up rejected: Minimum top-up is ₹100.00.");
            return;
        }

        if (balance + amount > 5000) {
            System.out.printf(
                    "Top-up rejected: Maximum balance is ₹5000.00.%n"
            );
            return;
        }

        recordTransaction(amount);

        System.out.printf(
                "%s topped up with ₹%.2f. Balance: ₹%.2f.%n",
                cardId, amount, balance
        );
    }

    public void purchase(String itemName, double originalPrice) {
        if (blocked) {
            System.out.println("Purchase failed: Card is blocked.");
            return;
        }

        double chargedAmount = pricingPlan.calculatePrice(originalPrice);

        if (balance < chargedAmount) {
            System.out.printf(
                    "Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",
                    chargedAmount, balance
            );
            return;
        }

        recordTransaction(-chargedAmount);

        CanteenPurchase purchase =
                new CanteenPurchase(itemName, chargedAmount);

        purchases.add(purchase);

        System.out.printf(
                "%s purchased for ₹%.2f. Balance: ₹%.2f.%n",
                itemName, chargedAmount, balance
        );
    }

    public void refund(String itemName) {
        for (CanteenPurchase purchase : purchases) {

            if (purchase.getItemName().equals(itemName)) {

                if (purchase.isRefunded()) {
                    System.out.printf(
                            "Refund rejected: %s has already been refunded.%n",
                            itemName
                    );
                    return;
                }

                double refundAmount = purchase.getChargedAmount();

                recordTransaction(refundAmount);
                purchase.markRefunded();

                System.out.printf(
                        "Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n",
                        refundAmount, itemName, balance
                );

                return;
            }
        }

        System.out.println(
                "Refund rejected: Purchase not found."
        );
    }

    public void block() {
        blocked = true;
        System.out.println("Card " + cardId + " blocked.");
    }

    public void unblock() {
        blocked = false;
        System.out.println("Card " + cardId + " unblocked.");
    }

    public void miniStatement() {
        System.out.print(
                "Mini-statement for " + cardId + ": "
        );

        for (int i = 0; i < transactions.size(); i++) {

            double amount = transactions.get(i).getAmount();

            if (amount >= 0) {
                System.out.printf("+%.2f", amount);
            } else {
                System.out.printf("%.2f", amount);
            }

            if (i < transactions.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.printf(
                " = ₹%.2f.%n",
                balance
        );
    }

    /*
     * The only method that changes balance.
     * Every balance change is recorded first.
     */
    private void recordTransaction(double amount) {
        double newBalance = balance + amount;

        if (newBalance < 0) {
            throw new IllegalStateException(
                    "Balance cannot become negative."
            );
        }

        balance = newBalance;
        transactions.add(new CanteenTransaction(amount));
    }
}

public class CampusCanteenSmartCard {
    public static void main(String[] args) {

        CanteenPricingPlan hostellerPlan =
                new CanteenHostellerPlan();

        CanteenSmartCard card =
                new CanteenSmartCard("C-2045", hostellerPlan);

        // 1. Top-up ₹500
        card.topUp(500);

        // 2. Veg Thali ₹120 → 10% discount = ₹108
        card.purchase("Veg Thali", 120);

        // 3. Cold Coffee ₹60 → 10% discount = ₹54
        card.purchase("Cold Coffee", 60);

        // 4. Attempt ₹400 → discounted price ₹360
        card.purchase("Items worth ₹400", 400);

        // 5. Refund Veg Thali
        card.refund("Veg Thali");

        // 6. Refund Veg Thali again
        card.refund("Veg Thali");

        // 7. Mini-statement
        card.miniStatement();
    }
}
