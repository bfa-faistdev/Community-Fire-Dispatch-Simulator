package at.faistdev.fwlstsim.dataaccess.entities;

public class Waypoint {

    private final Location location;

    private final long ticksToPreviousWaypoint;

    public Waypoint(Location location, long ticksToPreviousWaypoint) {
        this.location = location;
        this.ticksToPreviousWaypoint = ticksToPreviousWaypoint;
    }

    public Location getLocation() {
        return location;
    }

    public long getTicksToPreviousWaypoint() {
        return ticksToPreviousWaypoint;
    }

    @Override
    public String toString() {
        return "Waypoint(" + location + "," + ticksToPreviousWaypoint + ")";
    }

}
