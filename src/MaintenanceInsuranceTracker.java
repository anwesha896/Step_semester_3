abstract class MaintenanceServiceableVehicle {
    private double mileage;
    public abstract String performMaintenance();
    public double getMileage() {
        return mileage;
    }
    public void addMileage(double km) {
        if (km < 0) {
            return;
        }
        mileage += km;
    }
}
interface MaintenanceInsurable {
    String getInsuranceInfo();
}
class MaintenanceForklift extends MaintenanceServiceableVehicle
        implements MaintenanceInsurable {
    private String assetTag;
    public MaintenanceForklift(String assetTag) {
        this.assetTag = assetTag;
    }
    @Override
    public String performMaintenance() {
        return "Forklift " + assetTag + ": hydraulic and fork inspection complete";
    }
    @Override
    public String getInsuranceInfo() {
        return "Insured under fleet policy - Asset " + assetTag;
    }
}
class MaintenanceHeavyDutyForklift extends MaintenanceForklift {
    public MaintenanceHeavyDutyForklift(String assetTag) {
        super(assetTag);
    }
    @Override
    public String performMaintenance() {
        return super.performMaintenance() + " | high-pressure hydraulic check complete";
    }
}
public class MaintenanceInsuranceTracker {
    static String getInsuranceIfApplicable(MaintenanceServiceableVehicle v) {
        if (v instanceof MaintenanceInsurable) {
            MaintenanceInsurable i = (MaintenanceInsurable) v;
            return i.getInsuranceInfo();
        }
        return "No insurance record exists";
    }
    public static void main(String[] args) {
        MaintenanceForklift f = new MaintenanceForklift("FL-22");
        f.addMileage(120);
        System.out.println(f.getMileage());
        System.out.println(f.performMaintenance());
        MaintenanceHeavyDutyForklift hd = new MaintenanceHeavyDutyForklift("HD-9");
        System.out.println(hd.performMaintenance());
        System.out.println(getInsuranceIfApplicable(f));
    }
}


