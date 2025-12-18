package at.faistdev.fwlstsim.dataaccess.entities;

public abstract class VehicleTarget {

    private final VehicleStatus newVehicleStatus;

    public VehicleTarget(VehicleStatus newVehicleStatus) {
        this.newVehicleStatus = newVehicleStatus;
    }

    public abstract boolean isReachedTarget(long currentTick);

    public VehicleStatus getNewVehicleStatus() {
        return newVehicleStatus;
    }

}
