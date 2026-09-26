import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Capability abstraction
interface LabCapability {
    String getCapabilityName();

    boolean supports(String operation);

    boolean apply(String operation, double value, String deviceName);
}

// Power capability
class LabPowerCapability implements LabCapability {

    private boolean poweredOn;

    public LabPowerCapability() {
        poweredOn = false;
    }

    public String getCapabilityName() {
        return "Power";
    }

    public boolean supports(String operation) {
        return operation.equals("ON") || operation.equals("OFF");
    }

    public boolean apply(
            String operation,
            double value,
            String deviceName) {

        if (operation.equals("ON")) {
            poweredOn = true;
            System.out.println(deviceName + ": ON");
            return true;
        }

        if (operation.equals("OFF")) {
            poweredOn = false;
            System.out.println(deviceName + ": OFF");
            return true;
        }

        return false;
    }
}

// Brightness capability
class LabBrightnessCapability implements LabCapability {

    private double brightness;

    public LabBrightnessCapability() {
        brightness = 0;
    }

    public String getCapabilityName() {
        return "Brightness";
    }

    public boolean supports(String operation) {
        return operation.equals("SET_BRIGHTNESS");
    }

    public boolean apply(
            String operation,
            double value,
            String deviceName) {

        if (!supports(operation)) {
            return false;
        }

        if (value < 0 || value > 100) {
            System.out.println(
                    "Rejected: " + deviceName
                            + " brightness must be between 0% and 100%."
            );
            return false;
        }

        brightness = value;

        System.out.println(
                deviceName + ": brightness set to "
                        + String.format("%.0f", brightness) + "%."
        );

        return true;
    }
}

// Temperature capability
class LabTemperatureCapability implements LabCapability {

    private double temperature;

    public LabTemperatureCapability() {
        temperature = 24;
    }

    public String getCapabilityName() {
        return "Temperature";
    }

    public boolean supports(String operation) {
        return operation.equals("SET_TEMPERATURE");
    }

    public boolean apply(
            String operation,
            double value,
            String deviceName) {

        if (!supports(operation)) {
            return false;
        }

        if (value < 16 || value > 30) {
            System.out.println(
                    "Rejected: " + deviceName
                            + " temperature must be between 16°C and 30°C."
            );
            return false;
        }

        temperature = value;

        System.out.println(
                deviceName + ": temperature set to "
                        + String.format("%.0f", temperature) + "°C."
        );

        return true;
    }
}

// Smart device
class LabSmartDevice {

    private String deviceName;
    private Map<String, LabCapability> capabilities;

    public LabSmartDevice(String deviceName) {
        this.deviceName = deviceName;
        this.capabilities = new LinkedHashMap<>();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void addCapability(LabCapability capability) {

        String capabilityName =
                capability.getCapabilityName();

        if (capabilities.containsKey(capabilityName)) {
            return;
        }

        capabilities.put(
                capabilityName,
                capability
        );

        System.out.println(
                deviceName + ": "
                        + capabilityName
                        + " capability added."
        );
    }

    public boolean hasCapability(String capabilityName) {
        return capabilities.containsKey(capabilityName);
    }

    public boolean execute(
            String operation,
            double value) {

        for (LabCapability capability :
                capabilities.values()) {

            if (capability.supports(operation)) {
                return capability.apply(
                        operation,
                        value,
                        deviceName
                );
            }
        }

        return false;
    }
}

// Scene step
class LabSceneStep {

    private String capabilityName;
    private String operation;
    private double value;

    public LabSceneStep(
            String capabilityName,
            String operation,
            double value) {

        this.capabilityName = capabilityName;
        this.operation = operation;
        this.value = value;
    }

    public int execute(
            List<LabSmartDevice> devices) {

        int actionsApplied = 0;

        for (LabSmartDevice device : devices) {

            if (device.hasCapability(capabilityName)) {

                boolean success =
                        device.execute(
                                operation,
                                value
                        );

                if (success) {
                    actionsApplied++;
                }
            }
        }

        return actionsApplied;
    }
}

// Scene
class LabControlScene {

    private String sceneName;
    private List<LabSceneStep> steps;

    public LabControlScene(String sceneName) {
        this.sceneName = sceneName;
        this.steps = new ArrayList<>();
    }

    public void addStep(LabSceneStep step) {
        steps.add(step);
    }

    public void execute(
            List<LabSmartDevice> devices) {

        System.out.println(
                "Scene '" + sceneName + "' started."
        );

        int totalActions = 0;

        for (LabSceneStep step : steps) {
            totalActions += step.execute(devices);
        }

        System.out.println(
                "Scene '" + sceneName
                        + "' completed: "
                        + totalActions
                        + " actions applied."
        );
    }
}

// Main class
public class SmartLabControlPanel {

    public static void main(String[] args) {

        // Create devices
        LabSmartDevice labAC =
                new LabSmartDevice("Lab AC");

        LabSmartDevice ceilingLights =
                new LabSmartDevice("Ceiling Lights");

        LabSmartDevice projector =
                new LabSmartDevice("Projector");

        // Add capabilities
        labAC.addCapability(
                new LabPowerCapability()
        );

        labAC.addCapability(
                new LabTemperatureCapability()
        );

        ceilingLights.addCapability(
                new LabPowerCapability()
        );

        ceilingLights.addCapability(
                new LabBrightnessCapability()
        );

        projector.addCapability(
                new LabPowerCapability()
        );

        List<LabSmartDevice> devices =
                new ArrayList<>();

        devices.add(labAC);
        devices.add(ceilingLights);
        devices.add(projector);

        // Create Lecture Mode
        LabControlScene lectureMode =
                new LabControlScene("Lecture Mode");

        // Turn everything ON
        lectureMode.addStep(
                new LabSceneStep(
                        "Power",
                        "ON",
                        1
                )
        );

        // Set brightness to 40%
        lectureMode.addStep(
                new LabSceneStep(
                        "Brightness",
                        "SET_BRIGHTNESS",
                        40
                )
        );

        // Set temperature to 24°C
        lectureMode.addStep(
                new LabSceneStep(
                        "Temperature",
                        "SET_TEMPERATURE",
                        24
                )
        );

        // Execute scene
        lectureMode.execute(devices);

        // Invalid temperature
        labAC.execute(
                "SET_TEMPERATURE",
                12
        );

        // Add Brightness capability at runtime
        projector.addCapability(
                new LabBrightnessCapability()
        );

        // Set projector brightness
        projector.execute(
                "SET_BRIGHTNESS",
                70
        );
    }
}
