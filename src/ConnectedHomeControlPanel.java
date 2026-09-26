abstract class HomeControlDevice {
    private static int serialCounter = 1000;
    private final String serialNumber;
    HomeControlDevice() {
        serialNumber = "HD-" + (++serialCounter);
    }
    public String getSerialNumber() {
        return serialNumber;
    }
    public abstract String activate();
}
interface HomeRemoteControllable {
    String connect(String appId);
}
interface HomeEnergyTrackable {
    double getConsumptionWatts();
}
class HomeWashingMachine extends HomeControlDevice implements HomeRemoteControllable, HomeEnergyTrackable {
    private double consumptionWatts;
    public HomeWashingMachine(double consumptionWatts) {
        this.consumptionWatts = consumptionWatts;
    }
    @Override
    public String activate() {
        return "Washing machine " + getSerialNumber() + " started a cycle";
    }
    @Override
    public String connect(String appId) {
        return getSerialNumber() + " connected to " + appId;
    }
    @Override
    public double getConsumptionWatts() {
        return consumptionWatts;
    }
}
class HomeRefrigerator extends HomeControlDevice implements HomeEnergyTrackable {
    private double consumptionWatts;
    public HomeRefrigerator(double consumptionWatts) {
        this.consumptionWatts = consumptionWatts;
    }
    @Override
    public String activate() {
        return "Refrigerator " + getSerialNumber() + " activated";
    }
    @Override
    public double getConsumptionWatts() {
        return consumptionWatts;
    }
}
class HomeMobileApp implements HomeRemoteControllable {
    private String appName;
    public HomeMobileApp(String appName) {
        this.appName = appName;
    }
    @Override
    public String connect(String appId) {
        return appName + " connected to " + appId;
    }
}
public class ConnectedHomeControlPanel {
    static void connectAll(HomeRemoteControllable[] items, String appId) {
        for (HomeRemoteControllable item : items) {
            System.out.println(item.connect(appId));
        }
    }
    static double getConsumptionIfTrackable(HomeControlDevice d) {
        if (d instanceof HomeEnergyTrackable) {
            HomeEnergyTrackable e = (HomeEnergyTrackable) d;
            return e.getConsumptionWatts();
        }
        return 0.0;
    }
    public static void main(String[] args) {
        HomeWashingMachine wm = new HomeWashingMachine(500.0);
        System.out.println(wm.activate());
        System.out.println(wm.connect("HomeConnect"));
        HomeRefrigerator fridge = new HomeRefrigerator(150.0);
        System.out.println(getConsumptionIfTrackable(fridge));
        HomeMobileApp app = new HomeMobileApp("HomeConnect App");
        System.out.println(app.connect("HomeConnect"));
        HomeControlDevice ref = wm;   // Upcasting
        System.out.println(getConsumptionIfTrackable(ref));
        connectAll(new HomeRemoteControllable[]{wm, app}, "HomeConnect");
    }
}