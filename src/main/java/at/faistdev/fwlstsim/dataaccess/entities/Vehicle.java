package at.faistdev.fwlstsim.dataaccess.entities;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {

    private final List<OperationResource> resources;
    private VehicleStatus status;
    private final List<VehicleTarget> targets = new ArrayList<>();

    public Vehicle(List<OperationResource> resources) {
        status = VehicleStatus.STATUS_9;
        this.resources = resources;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
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

    public VehicleTarget getNextTarget() {
        if (targets.isEmpty()) {
            return null;
        }

        return targets.get(0);
    }
}
