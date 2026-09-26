class MedicalReport {
    private final String patientId;
    private final String[] medicineCodes;
    public MedicalReport(String patientId, String[] medicineCodes) {
        if (patientId == null) {
            throw new IllegalArgumentException("construction rejected");
        }
        for (String code : medicineCodes) {
            if (code == null || !code.matches("MED-[A-Z]")) {
                throw new IllegalArgumentException("construction rejected");
            }
        }
        this.patientId = patientId;
        this.medicineCodes = medicineCodes.clone();
    }
    public String getPatientId() {
        return patientId;
    }
    public String[] getMedicationCodes() {
        return medicineCodes.clone();
    }
    public MedicalReport withCorrectedMedication(int index, String newCode) {
        if (newCode == null || !newCode.matches("MED-[A-Z]")) {
            throw new IllegalArgumentException("construction rejected");
        }
        String[] updated = medicineCodes.clone();
        updated[index] = newCode;
        return new MedicalReport(patientId, updated);
    }
}
class IntensiveCareReport extends MedicalReport {
    private final int icuDays;
    public IntensiveCareReport(String patientId, String[] medicineCodes, int icuDays) {
        super(patientId, medicineCodes);
        this.icuDays = icuDays;
    }
    public int getIcuDays() {
        return icuDays;
    }
}
class BatchReconciler {
    private static String hospitalName;
    static {
        hospitalName = "MediTrack";
        System.out.println("Nightly Processor Initialized");
    }
    public static String processNightlyBatch(MedicalReport[] reports) {
        int processed = 0;
        int nullSkipped = 0;
        int critical = 0;
        int routine = 0;
        for (MedicalReport report : reports) {
            if (report == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (report instanceof IntensiveCareReport) {
                critical++;
            } else {
                routine++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + critical + " critical-care | "
                + routine + " routine";
    }
    public static void main(String[] args) {
        try {
            MedicalReport r1 = new MedicalReport("MT2026-0142", new String[]{"MED-A", "MED-B"});
            String[] copy = r1.getMedicationCodes();
            copy[0] = "TAMPERED";
            System.out.println(r1.getMedicationCodes()[0]);
            MedicalReport corrected = r1.withCorrectedMedication(1, "MED-C");
            System.out.println(corrected.getMedicationCodes()[1]);
            MedicalReport[] reports = {new IntensiveCareReport("MT001", new String[]{"MED-X"}, 4),
                    null, new MedicalReport("MT002", new String[]{"MED-Y"})};
            System.out.println(processNightlyBatch(reports));
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}


