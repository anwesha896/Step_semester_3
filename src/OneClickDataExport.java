interface Exportable {
    String exportData();
}
class ExportCounter {
    private static int totalExports = 0;
    public static void increment() {
        totalExports++;
    }
    public static int getTotalExports() {
        return totalExports;
    }
}
class ExportReportGenerator implements Exportable {
    private String reportName;
    public ExportReportGenerator(String reportName) {
        this.reportName = reportName;
    }
    @Override
    public String exportData() {
        ExportCounter.increment();
        return "Exported report: " + reportName;
    }
}
class ExportUserProfile implements Exportable {
    private String username;
    public ExportUserProfile(String username) {
        this.username = username;
    }
    @Override
    public String exportData() {
        ExportCounter.increment();
        return "Exported profile: " + username;
    }
}
public class OneClickDataExport {
    static int getTotalExports() {
        return ExportCounter.getTotalExports();
    }
    static void exportAll(Exportable[] items) {
        for (Exportable item : items) {
            System.out.println(item.exportData());
        }
    }
    public static void main(String[] args) {
        ExportReportGenerator r = new ExportReportGenerator("Sales Q1");
        System.out.println(r.exportData());
        ExportUserProfile u = new ExportUserProfile("jane_doe");
        System.out.println(u.exportData());
        Exportable ref = r;   // Upcasting
        exportAll(new Exportable[]{ref, u});
        System.out.println("Total exports: " + getTotalExports());
    }
}


