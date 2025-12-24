package at.faistdev.fwlstsim.dataaccess.entities;

import java.util.ArrayList;
import java.util.List;

public class VehicleTarget {

    private final Location location;
    private final VehicleStatus newVehicleStatus;
    private List<Waypoint> route = new ArrayList();
    private long lastWaypointReachedTick = 0;

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

    public List<Waypoint> getRoute() {
        return route;
    }

    public void setRoute(List<Waypoint> route) {
        this.route = route;
    }

    public Waypoint getNextWaypoint() {
        if (route.isEmpty()) {
            return null;
        }

        return route.get(0);
    }

    public long getLastWaypointReachedTick() {
        return lastWaypointReachedTick;
    }

    public void setLastWaypointReachedTick(long lastWaypointReachedTick) {
        this.lastWaypointReachedTick = lastWaypointReachedTick;
    }

    public void removeWaypoint(Waypoint waypoint) {
        route.remove(waypoint);
    }
}
