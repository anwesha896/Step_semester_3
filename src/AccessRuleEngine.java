class AccessRuleEngine {
    static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier.equals("private")) {
            if (accessorContext.equals("SAME_CLASS"))
                return "ALLOWED";
            else
                return "DENIED";
        }
        if (fieldModifier.equals("default")) {
            if (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE"))
                return "ALLOWED";
            else
                return "DENIED";
        }
        if (fieldModifier.equals("protected")) {
            if (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE"))
                return "ALLOWED";
            else
                return "DENIED";
        }
        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }
        return "DENIED";
    }
    static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;
        for (int i = 0; i < attempts.length; i++) {
            String result = classifyAccess(attempts[i][0], attempts[i][1]);
            if (result.equals("ALLOWED"))
                allowed++;
            else
                denied++;
        }
        return "Allowed: " + allowed + " | Denied: " + denied;
    }
    public static void main(String[] args) {
        System.out.println(classifyAccess("private", "SAME_CLASS"));
        System.out.println(classifyAccess("default", "DIFFERENT_PACKAGE"));
        String[][] attempts = {{"protected", "SAME_PACKAGE"}, {"protected", "DIFFERENT_PACKAGE"},
                {"public", "DIFFERENT_PACKAGE"}};
        System.out.println(summarizeBatch(attempts));
        try {
            PatientRecord p = new PatientRecord("MT9", "W3", 98.2,
                            "MediTrack Central");
            System.out.println("PatientRecord created");
        }
        catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
        try {
            PatientRecord p = new PatientRecord("MT94", "W3", 98.2,
                            "MediTrack Central");
            System.out.println("PatientRecord created");
        }
        catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
    }
}
class PatientRecord {
    private String patientId;
    private String wardCode;
    private double vitalsScore;
    public String facilityName;

    public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
        String trimmedId = patientId.trim();
        if (trimmedId.length() == 0 || trimmedId.length() < 4) {
            throw new IllegalArgumentException(
                    "construction rejected"
            );
        }
        this.patientId = trimmedId;
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;
    }
}


