package at.faistdev.fwlstsim.bl.service;

import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import at.faistdev.fwlstsim.dataaccess.entities.VehicleStatus;
import at.faistdev.fwlstsim.dataaccess.entities.VehicleTarget;

public class RoutingService {

    public static void doRouting(long currentTick, Vehicle vehicle) {
        VehicleTarget nextTarget = vehicle.getNextTarget();
        if (nextTarget.isReachedTarget(currentTick)) {
            VehicleStatus newVehicleStatus = nextTarget.getNewVehicleStatus();
            if (newVehicleStatus != null) {
                vehicle.setStatus(newVehicleStatus);
            }

            vehicle.removeTarget(nextTarget);
        }
    }

}
