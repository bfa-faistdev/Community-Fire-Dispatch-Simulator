package at.faistdev.fwlstsim.dataaccess.entities;

public class VehicleTarget {

    private final Location location;
    private final VehicleStatus newVehicleStatus;

    public VehicleTarget(Location location, VehicleStatus newVehicleStatus) {
        this.location = location;
        this.newVehicleStatus = newVehicleStatus;
    }

    public VehicleStatus getNewVehicleStatus() {
        return newVehicleStatus;
    }

    public Location getLocation() {
        return location;
    }

}
