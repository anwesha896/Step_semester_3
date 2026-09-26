interface HomeAlertable {
    String sendAlert(String message);
}
class HomeSecuritySensor {
    protected String zoneName;
    public HomeSecuritySensor(String zoneName) {
        this.zoneName = zoneName;
    }
    public String getZoneName() {
        return zoneName;
    }
}
class HomeMotionSensor extends HomeSecuritySensor
        implements HomeAlertable {
    public HomeMotionSensor(String zoneName) {
        super(zoneName);
    }
    @Override
    public String sendAlert(String message) {
        return "[" + zoneName + "] " + message;
    }
}
class HomeDualZoneMotionSensor extends HomeMotionSensor {
    private String secondZoneName;
    public HomeDualZoneMotionSensor(
            String zoneName,
            String secondZoneName) {
        super(zoneName);
        this.secondZoneName = secondZoneName;
    }
    @Override
    public String sendAlert(String message) {
        String result = super.sendAlert(message);
        return result + " [also covering " + secondZoneName + "]";
    }
}
class HomeSmokeDetector implements HomeAlertable {
    private String deviceId;
    public HomeSmokeDetector(String deviceId) {
        this.deviceId = deviceId;
    }
    @Override
    public String sendAlert(String message) {
        return "[" + deviceId + "] " + message;
    }
}
public class HomeSafetyAlertNetwork {
    static void broadcastAll(
            HomeAlertable[] devices, String message) {
        for (HomeAlertable device : devices) {
            System.out.println(device.sendAlert(message));
        }
    }
    static String getZoneIfMotionSensor(HomeAlertable device) {
        if (device instanceof HomeMotionSensor) {
            HomeMotionSensor motion = (HomeMotionSensor) device;
            return motion.getZoneName();
        }
        return "Not a motion sensor";
    }
    public static void main(String[] args) {
        HomeMotionSensor motion = new HomeMotionSensor("Living Room");
        HomeDualZoneMotionSensor dual = new HomeDualZoneMotionSensor("Hallway", "Stairwell");
        HomeSmokeDetector smoke = new HomeSmokeDetector("SD-01");
        System.out.println(motion.sendAlert("Motion detected"));
        System.out.println(dual.sendAlert("Motion detected"));
        System.out.println(smoke.sendAlert("Smoke detected"));
        System.out.println();
        HomeAlertable[] devices = {motion, dual, smoke};
        broadcastAll(devices, "Emergency detected");
        System.out.println();
        System.out.println(getZoneIfMotionSensor(motion));
        System.out.println(getZoneIfMotionSensor(smoke));
    }
}


