class MedicalProfile {
    private String patientId;
    private String patientName;
    private boolean discharged;
    private String lockerPin;
    // No-argument constructor
    public MedicalProfile() {
        this(null, null);
    }
    // Name-only constructor
    public MedicalProfile(String patientName) {
        this(null, patientName);
    }
    // ID + Name constructor
    public MedicalProfile(String patientId, String patientName) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.discharged = false;
    }
    // Patient ID (write-once)
    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String id) {
        if (this.patientId == null) {
            this.patientId = id;
        }
    }
    // Patient Name
    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    // Discharge Status
    public boolean isDischarged() {
        return discharged;
    }
    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }
    // Write-only Locker PIN
    public void setLockerPin(String pin) {
        if (pin != null && pin.matches("\\d{4,6}")) {
            // Simple one-way transformation
            lockerPin = Integer.toHexString(pin.hashCode());
        }
    }
    public static void main(String[] args) {
        MedicalProfile p1 = new MedicalProfile("Arjun Iyer");
        System.out.println("Patient ID: " + p1.getPatientId());
        MedicalProfile p2 = new MedicalProfile("MT2026-0142", "Arjun Iyer");
        System.out.println("Patient ID: " + p2.getPatientId());
        MedicalProfile p3 = new MedicalProfile();
        p3.setPatientId("MT2026-0142");
        p3.setPatientId("HACKED-0000");
        System.out.println("Patient ID: " + p3.getPatientId());
        p3.setDischarged(true);
        System.out.println("Discharged: " + p3.isDischarged());
        p3.setLockerPin("1234");
        System.out.println("Program Executed Successfully");
    }
}



