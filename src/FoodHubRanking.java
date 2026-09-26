class FoodHub {
    String canteenCode;
    String canteenName;
    int trustScore;
    // Main constructor
    public FoodHub(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }
    // Constructor chaining
    public FoodHub(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }
    // Comparison method
    int compareTo(FoodHub other) {
        // 1. Higher trust score comes first
        if (this.trustScore > other.trustScore) {
            return -1;
        }
        else if (this.trustScore < other.trustScore) {
            return 1;
        }
        // 2. If score is same, compare code ignoring case
        int codeResult = this.canteenCode.compareToIgnoreCase(other.canteenCode);
        if (codeResult != 0) {
            return codeResult;
        }
        // 3. If code is also same, shorter name comes first
        if (this.canteenName.length() < other.canteenName.length()) {
            return -1;
        }
        else if (this.canteenName.length() > other.canteenName.length()) {
            return 1;
        }
        // Completely equal
        return 0;
    }
    // Manual ranking using insertion sort
    static FoodHub[] rankCanteens(FoodHub[] canteens) {
        FoodHub[] result = new FoodHub[canteens.length];
        // Copy original array
        for (int i = 0; i < canteens.length; i++) {
            result[i] = canteens[i];
        }
        // Insertion sort
        for (int i = 1; i < result.length; i++) {
            FoodHub current = result[i];
            int j = i - 1;
            while (j >= 0 && result[j].compareTo(current) > 0) {
                result[j + 1] = result[j];
                j--;
            }
            result[j + 1] = current;
        }
        return result;
    }
}
public class FoodHubRanking {
    public static void main(String[] args) {
        FoodHub[] canteens = {new FoodHub("HB3-C", "Spice Junction", 3),
                new FoodHub("hb1-c", "Grand Mess", 5),
                new FoodHub("HB2-C", "Southern Treats")};
        FoodHub[] ranked = FoodHub.rankCanteens(canteens);
        System.out.println("Ranked Canteens:");
        for (int i = 0; i < ranked.length; i++) {
            System.out.println(ranked[i].canteenCode);
        }
    }
}


