class VisibilityChecker {
    static String checkAccess(String modifier, String context) {
        if (modifier.equals("private")) {
            if (context.equals("SAME_CLASS"))
                return "ALLOWED";
            return "DENIED";
        }
        if (modifier.equals("default")) {
            if (context.equals("SAME_CLASS") || context.equals("SAME_PACKAGE"))
                return "ALLOWED";
            return "DENIED";
        }
        if (modifier.equals("protected")) {
            if (context.equals("SAME_CLASS") || context.equals("SAME_PACKAGE") ||
                    context.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"))
                return "ALLOWED";
            return "DENIED";
        }
        if (modifier.equals("public")) {
            return "ALLOWED";
        }
        return "DENIED";
    }
    static String formatContext(String context) {
        String[] parts = context.split("_");
        String output = "";
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            part = part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase();
            output += part;
            if (i < parts.length - 1) {
                output += " ";
            }
        }
        return output;
    }
    public static void main(String[] args) {
        System.out.println(checkAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println(checkAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println(formatContext("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
    }
}


