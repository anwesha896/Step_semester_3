import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

abstract class PlatformDeviceBase {
    protected String id;
    protected boolean isOn;

    public PlatformDeviceBase(String id) {
        this.id = id;
        this.isOn = false;
    }

    public void turnOn() {
        isOn = true;
        System.out.println(id + " is ON");
    }

    public void turnOff() {
        isOn = false;
        System.out.println(id + " is OFF");
    }
}

interface PlatformDimmable {
    void dim(int level);
}

interface PlatformSchedulable {
    void schedule(String time);
}

interface PlatformEnergyMonitor {
    void showEnergy();
}

class PlatformLight extends PlatformDeviceBase
        implements PlatformDimmable, PlatformSchedulable {

    public PlatformLight(String id) {
        super(id);
    }

    public void dim(int level) {
        System.out.println(id + " dimmed to " + level);
    }

    public void schedule(String time) {
        System.out.println(id + " scheduled " + time);
    }
}

class PlatformFan extends PlatformDeviceBase
        implements PlatformSchedulable {

    public PlatformFan(String id) {
        super(id);
    }

    public void schedule(String time) {
        System.out.println(id + " scheduled " + time);
    }
}

class PlatformPlug extends PlatformDeviceBase
        implements PlatformEnergyMonitor {

    private int energy;

    public PlatformPlug(String id, int energy) {
        super(id);
        this.energy = energy;
    }

    public void showEnergy() {
        System.out.println(id + " energy " + energy + " kWh");
    }
}

public class SmartHomePlatform {

    private Map<String, PlatformDeviceBase> devices =
            new HashMap<>();

    public SmartHomePlatform() {
        devices.put("L1", new PlatformLight("L1"));
        devices.put("F1", new PlatformFan("F1"));
        devices.put("P1", new PlatformPlug("P1", 12));
    }

    public void processCommand(String command) {
        String[] parts = command.trim().split("\\s+");

        String operation = parts[0];
        String id = parts[1];

        PlatformDeviceBase device = devices.get(id);

        if (device == null) {
            System.out.println(id + " rejected: unknown device");
            return;
        }

        switch (operation) {
            case "ON":
                device.turnOn();
                break;

            case "OFF":
                device.turnOff();
                break;

            case "DIM":
                if (device instanceof PlatformDimmable) {
                    int level = Integer.parseInt(parts[2]);

                    if (level < 0 || level > 100) {
                        System.out.println(id + " rejected: invalid dim level");
                    } else {
                        ((PlatformDimmable) device).dim(level);
                    }
                } else {
                    System.out.println(id + " rejected: DIM unsupported");
                }
                break;

            case "SCHEDULE":
                if (device instanceof PlatformSchedulable) {
                    ((PlatformSchedulable) device)
                            .schedule(parts[2]);
                } else {
                    System.out.println(id + " rejected: SCHEDULE unsupported");
                }
                break;

            case "ENERGY":
                if (device instanceof PlatformEnergyMonitor) {
                    ((PlatformEnergyMonitor) device).showEnergy();
                } else {
                    System.out.println(id + " rejected: ENERGY unsupported");
                }
                break;

            default:
                System.out.println(id + " rejected: unknown command");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SmartHomePlatform platform = new SmartHomePlatform();

        System.out.print("Enter number of commands: ");
        int n = Integer.parseInt(sc.nextLine().trim());

        for (int i = 0; i < n; i++) {
            String command = sc.nextLine();
            platform.processCommand(command);
        }

        sc.close();
    }
}