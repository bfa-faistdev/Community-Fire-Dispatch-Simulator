package at.faistdev.fwlstsim.dataaccess.entities;

public class VehicleTickTarget extends VehicleTarget {

    private final long willArriveAtTick;

    public VehicleTickTarget(long willArriveAtTick, VehicleStatus newVehicleStatus) {
        super(newVehicleStatus);
        this.willArriveAtTick = willArriveAtTick;
    }

    @Override
    public boolean isReachedTarget(long currentTick) {
        return currentTick >= willArriveAtTick;
    }
}
