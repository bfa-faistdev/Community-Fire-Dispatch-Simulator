package at.faistdev.fwlstsim.dataaccess.entities;

import at.faistdev.fwlstsim.ui.RadioUi;
import at.faistdev.fwlstsim.ui.UiRegistry;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {

    private final String name;
    private final List<OperationResource> resources;
    private VehicleStatus status;
    private final VehicleHome home;
    private Location currentLocation;
    private final List<VehicleTarget> targets = new ArrayList<>();
    private final List<RadioMessage> pendingRadioMessages = new ArrayList<>();

    public Vehicle(String name, VehicleHome home, List<OperationResource> resources) {
        this.name = name;
        this.home = home;
        this.resources = resources;

        currentLocation = home.getLocationCopy();
        status = VehicleStatus.STATUS_9;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        System.err.println("" + this.getName() + ": Status " + this.getStatus().getText() + " to " + status.getText());
        this.status = status;
    }

    public List<OperationResource> getResources() {
        return resources;
    }

    public void addTarget(VehicleTarget target) {
        targets.add(target);
    }

    public void removeTarget(VehicleTarget target) {
        targets.remove(target);
    }

    public void clearTargets() {
        targets.clear();
    }

    public VehicleTarget getNextTarget() {
        if (targets.isEmpty()) {
            return null;
        }

        return targets.get(0);
    }

    public void addRadioMessage(RadioMessage message) {
        pendingRadioMessages.add(message);
        UiRegistry.get(RadioUi.class).fillRadioRequests();
    }

    public RadioMessage getNextRadioMessage() {
        if (pendingRadioMessages.isEmpty()) {
            return null;
        }

        return pendingRadioMessages.get(0);
    }

    public void removeNextRadioMessage() {
        if (pendingRadioMessages.isEmpty()) {
            return;
        }

        pendingRadioMessages.remove(0);
    }

    public VehicleHome getHome() {
        return home;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
        System.err.println(getName() + " updated location to " + currentLocation);
    }

    public void sendHome() {
        targets.clear();
        addTarget(new VehicleTarget(home.getLocationCopy(), null));
        setStatus(VehicleStatus.STATUS_9);
    }

    public String getName() {
        return name;
    }

}
