public class LibraryMemberJavaBean {
    private String membershipId;
    private String name;
    private boolean premiumMember;
    // Write-only property
    private String securityAnswer;
    // No-argument constructor
    public LibraryMemberJavaBean() {
        this(null, null);
    }
    // Name-only constructor
    public LibraryMemberJavaBean(String name) {
        this(null, name);
    }
    // ID + name constructor
    public LibraryMemberJavaBean(String membershipId, String name) {
        this.membershipId = membershipId;
        this.name = name;
        this.premiumMember = false;
    }
    // Getter for membershipId
    public String getMembershipId() {
        return membershipId;
    }
    // Setter for membershipId - write once only
    public void setMembershipId(String id) {
        if (this.membershipId == null) {
            this.membershipId = id;
        }
    }
    // Getter for name
    public String getName() {
        return name;
    }
    // Setter for name
    public void setName(String name) {
        this.name = name;
    }
    // JavaBean boolean getter
    public boolean isPremiumMember() {
        return premiumMember;
    }
    // Setter for premiumMember
    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }
    // Write-only security answer
    public void setSecurityAnswer(String answer) {
        if (answer == null) {
            this.securityAnswer = null;
        } else {
            // Simple deterministic one-way transformation
            this.securityAnswer = Integer.toHexString(
                    answer.hashCode()
            );
        }
    }
    public static void main(String[] args) {
        // Test 1: Name-only constructor
        LibraryMemberJavaBean m1 = new LibraryMemberJavaBean("Priya Nair");
        System.out.println(m1.getMembershipId());
        // Test 2: ID + name constructor
        LibraryMemberJavaBean m2 = new LibraryMemberJavaBean("LIB-8841", "Priya Nair");
        System.out.println(m2.getMembershipId());
        // Test 3: Write-once membership ID
        LibraryMemberJavaBean m3 = new LibraryMemberJavaBean();
        m3.setMembershipId("LIB-8841");
        m3.setMembershipId("FAKE-0000");
        System.out.println(m3.getMembershipId());
        // Test 4: JavaBean premium property
        m3.setPremiumMember(true);
        System.out.println(m3.isPremiumMember());
        // Test 5: Security answer is write-only
        m3.setSecurityAnswer("Blue");
        System.out.println("Security answer stored securely.");
    }
}


