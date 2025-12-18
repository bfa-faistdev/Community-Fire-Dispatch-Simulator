package at.faistdev.fwlstsim.bl.service;

import at.faistdev.fwlstsim.dataaccess.entities.Location;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import at.faistdev.fwlstsim.dataaccess.entities.VehicleStatus;
import at.faistdev.fwlstsim.dataaccess.entities.VehicleTarget;

public class RoutingService {

    public static void doRouting(Vehicle vehicle) {
        VehicleTarget nextTarget = vehicle.getNextTarget();
        moveNextRoutingPoint(vehicle, nextTarget.getLocation());

        if (hasReachedTarget(vehicle, nextTarget)) {
            VehicleStatus newVehicleStatus = nextTarget.getNewVehicleStatus();
            if (newVehicleStatus != null) {
                vehicle.setStatus(newVehicleStatus);
            }

            vehicle.removeTarget(nextTarget);
        }
    }

    private static boolean hasReachedTarget(Vehicle vehicle, VehicleTarget target) {
        return vehicle.getCurrentLocation().equals(target.getLocation());
    }

    private static void moveNextRoutingPoint(Vehicle vehicle, Location location) {
        vehicle.setCurrentLocation(location);
    }
}
