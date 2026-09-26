public class LoanReceiptLedger {
    private final String memberId;
    private final String[] bookIds;
    // Static block
    static {
        System.out.println("Library Circulation System Started");
    }
    // Constructor
    public LoanReceiptLedger(String memberId, String[] bookIds) {
        if (memberId == null || bookIds == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        // Validate all book IDs
        for (String id : bookIds) {
            if (id == null || !id.matches("BK-\\d{3}")) {
                throw new IllegalArgumentException(
                        "Invalid book ID"
                );
            }
        }
        this.memberId = memberId;
        // Defensive copy while storing
        this.bookIds = bookIds.clone();
    }
    // Defensive copy while returning
    public String[] getBookIds() {
        return bookIds.clone();
    }
    // With-style method
    public LoanReceiptLedger withCorrectedBookId(
            int index, String newId) {
        if (index < 0 || index >= bookIds.length) {
            throw new IndexOutOfBoundsException(
                    "Invalid index"
            );
        }
        if (newId == null || !newId.matches("BK-\\d{3}")) {
            throw new IllegalArgumentException(
                    "Invalid book ID"
            );
        }
        // Create a copy
        String[] corrected = bookIds.clone();
        // Modify only the copy
        corrected[index] = newId;
        // Return a brand-new object
        return new LoanReceiptLedger(
                memberId,
                corrected
        );
    }
    // Nightly circulation processor
    public static String processNightlyCirculation(LoanReceiptLedger[] receipts) {
        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;
        if (receipts == null) {
            return "0 processed | 0 null skipped | " + "0 reference-only | 0 regular";
        }
        for (LoanReceiptLedger receipt : receipts) {
            // Null check FIRST
            if (receipt == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            // Check for reference-only receipt
            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + referenceOnly + " reference-only | "
                + regular + " regular";
    }
    // Reference-only variant
    static class ReferenceOnlyLoanReceipt extends LoanReceiptLedger {
        private final String roomNumber;
        public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
            super(memberId, bookIds);
            this.roomNumber = roomNumber;
        }
    }
    public static void main(String[] args) {
        // Test 1: Invalid book ID
        try {
            LoanReceiptLedger r1 = new LoanReceiptLedger("LIB-8841", new String[]{"BK-100", "bad"});
        }
        catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
        // Test 2: Defensive copying
        LoanReceiptLedger r2 = new LoanReceiptLedger("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r2.getBookIds();
        ids[0] = "HACKED";
        System.out.println(r2.getBookIds()[0]);
        // Test 3: With-style correction
        LoanReceiptLedger corrected = r2.withCorrectedBookId(0, "BK-999");
        System.out.println(corrected.getBookIds()[0]);
        // Original remains unchanged
        System.out.println(r2.getBookIds()[0]);
        // Test 4: Nightly circulation
        LoanReceiptLedger[] receipts = {new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"},
                        "Reading Room 3"), null, new LoanReceiptLedger("LIB-002",
                new String[]{"BK-201"})};
        System.out.println(LoanReceiptLedger.processNightlyCirculation(receipts));
    }
}


