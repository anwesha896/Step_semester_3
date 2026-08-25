public class LibraryMember {

    // Broken version: these fields are static, so all objects share them.
    // name is wrong as static because each member has a different name.
    // memberId is wrong as static because each member needs a different ID.
    // booksIssued is wrong as static because each member has different books.
    static String name;
    static String memberId;
    static int booksIssued;

    // Fixed version
    String fixedName;
    String fixedMemberId;
    int fixedBooksIssued;

    static String libraryName = "Central Library";
    static int memberCount = 0;

    LibraryMember(String name, String memberId, int booksIssued) {
        // Broken version
        LibraryMember.name = name;
        LibraryMember.memberId = memberId;
        LibraryMember.booksIssued = booksIssued;

        // Fixed version
        this.fixedName = name;
        this.fixedBooksIssued = booksIssued;
        memberCount++;
        this.fixedMemberId = "LM-" + (1000 + memberCount);
    }

    void printMemberCard() {
        System.out.println(fixedName + " | " + fixedMemberId);
    }

    static void printTotalMembers() {
        System.out.println("Total members: " + memberCount);
    }

    public static void main(String[] args) {

        // Broken version
        LibraryMember a = new LibraryMember("Aditi", "LM-1001", 2);
        LibraryMember b = new LibraryMember("Rohan", "LM-1002", 3);

        System.out.println("Broken version:");
        System.out.println(a.name);
        System.out.println(b.name);

        // Fixed version
        System.out.println("Fixed version:");
        a.printMemberCard();
        b.printMemberCard();

        printTotalMembers();
    }
}