package at.faistdev.fwlstsim.dataaccess.entities;

public class VehicleHome {

    private final String name;
    private final Location location;

    public VehicleHome(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocationCopy() {
        return location.getCopy();
    }

}
