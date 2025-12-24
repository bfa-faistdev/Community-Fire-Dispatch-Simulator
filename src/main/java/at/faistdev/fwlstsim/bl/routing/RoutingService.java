package at.faistdev.fwlstsim.bl.routing;

import at.faistdev.fwlstsim.bl.service.OperationService;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import at.faistdev.fwlstsim.dataaccess.entities.VehicleStatus;
import at.faistdev.fwlstsim.dataaccess.entities.VehicleTarget;
import at.faistdev.fwlstsim.dataaccess.entities.Waypoint;

public class RoutingService {

    public static void doRouting(long currentTick, Vehicle vehicle) {
        VehicleTarget nextTarget = vehicle.getNextTarget();
        if (nextTarget == null) {
            return;
        }

        if (isVehicleDispatchedAndNeedsStatusUpdate(vehicle)) {
            vehicle.setStatus(VehicleStatus.STATUS_3);
        }

        if (nextTarget.getRoute().isEmpty()) {
            calculateRoute();
        }

        // ToDo: We could add a loop to do this forever until the condition is not met anymore
        Waypoint waypoint = nextTarget.getNextWaypoint();
        if (isWaypointReached(currentTick, nextTarget, waypoint)) {
            moveToWaypoint(currentTick, vehicle, nextTarget, waypoint);
        }

        if (isRouteComplete(nextTarget)) {
            moveToTarget(vehicle, nextTarget);
        }

        if (hasReachedTarget(vehicle, nextTarget)) {
            handleReachedTarget(vehicle, nextTarget);
        }
    }

    private static boolean hasReachedTarget(Vehicle vehicle, VehicleTarget target) {
        return vehicle.getCurrentLocation().equals(target.getLocation());
    }

    private static boolean isVehicleDispatchedAndNeedsStatusUpdate(Vehicle vehicle) {
        boolean vehicleDispatched = OperationService.isVehicleDispatched(vehicle);
        return vehicleDispatched && vehicle.getStatus() != VehicleStatus.STATUS_3;
    }

    private static void handleReachedTarget(Vehicle vehicle, VehicleTarget nextTarget) {
        VehicleStatus newVehicleStatus = nextTarget.getNewVehicleStatus();
        if (newVehicleStatus != null) {
            vehicle.setStatus(newVehicleStatus);
        }

        vehicle.removeTarget(nextTarget);
    }

    private static void calculateRoute() {
        // ToDo
    }

    private static boolean isWaypointReached(long currentTick, VehicleTarget nextTarget, Waypoint waypoint) {
        long expectedTickForWaypoint = nextTarget.getLastWaypointReachedTick() + waypoint.getTicksToPreviousWaypoint();
        if (currentTick >= expectedTickForWaypoint) {
            return true;
        }

        return false;
    }

    private static void moveToWaypoint(long currentTick, Vehicle vehicle, VehicleTarget nextTarget, Waypoint waypoint) {
        vehicle.setCurrentLocation(waypoint.getLocation());
        nextTarget.setLastWaypointReachedTick(currentTick);
        nextTarget.removeWaypoint(waypoint);
    }

    private static boolean isRouteComplete(VehicleTarget target) {
        return target.getRoute().isEmpty();
    }

    private static void moveToTarget(Vehicle vehicle, VehicleTarget target) {
        vehicle.setCurrentLocation(target.getLocation());
    }
}
