public class ReferenceDeskSubclassReach {
    // Classify access based on Java visibility rules
    static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier.equals("private")) {
            if (accessorContext.equals("SAME_CLASS")) {
                return "ALLOWED";
            }
            return "DENIED";
        }
        if (fieldModifier.equals("default")) {
            if (accessorContext.equals("SAME_CLASS") ||
                    accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }
        if (fieldModifier.equals("protected")) {
            if (accessorContext.equals("SAME_CLASS") ||
                    accessorContext.equals("SAME_PACKAGE") ||
                    accessorContext.equals(
                            "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }
        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }
        return "DENIED";
    }
    // Convert underscore-separated text into title case
    static String describeContext(String accessorContext) {
        String[] words = accessorContext.split("_");
        String result = "";
        for (String word : words) {
            String formatted = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            result = result + formatted + " ";
        }
        return result.trim();
    }
    public static void main(String[] args) {
        // Example 1
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        // Example 2
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        // Example 3
        System.out.println(describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
    }
}


