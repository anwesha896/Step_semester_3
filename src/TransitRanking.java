class TransitRoute {
    String routeCode;
    String routeName;
    int priority;
    // Main constructor
    public TransitRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }
    // Constructor chaining using this()
    public TransitRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 5);
    }
    // Comparison method
    int compareTo(TransitRoute other)
    {
        // 1. Compare priority
        if (this.priority < other.priority) {
            return -1;
        }
        else if (this.priority > other.priority) {
            return 1;
        }
        // 2. Compare route code ignoring case
        int codeResult = this.routeCode.compareToIgnoreCase(other.routeCode);
        if (codeResult != 0) {
            return codeResult;
        }
        // 3. Compare route name ignoring case
        return this.routeName.compareToIgnoreCase(other.routeName);
    }
    // Manual sorting
    static TransitRoute[] rankRoutes(TransitRoute[] routes) {
        TransitRoute[] result = new TransitRoute[routes.length];
        // Copy original array
        for (int i = 0; i < routes.length; i++) {
            result[i] = routes[i];
        }
        // Stable insertion sort
        for (int i = 1; i < result.length; i++) {
            TransitRoute current = result[i];
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
public class TransitRanking {
    public static void main(String[] args) {
        TransitRoute[] routes = {new TransitRoute("RT205L", "Airport Express", 3),
                new TransitRoute("rt201j", "City Central", 4),
                new TransitRoute("RT299T", "Night Service")
        };
        TransitRoute[] ranked = TransitRoute.rankRoutes(routes);
        System.out.println("Ranked Routes:");
        for (int i = 0; i < ranked.length; i++)
        {
            System.out.println(ranked[i].routeCode);
        }
    }
}


