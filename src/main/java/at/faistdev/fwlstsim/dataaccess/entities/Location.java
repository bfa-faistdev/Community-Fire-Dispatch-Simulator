package at.faistdev.fwlstsim.dataaccess.entities;

public class Location {

    private final String text;
    private final double lat;
    private final double lng;

    public Location(String text, double lat, double lng) {
        this.text = text;
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public Location getCopy() {
        return new Location(text, lat, lng);
    }

    public boolean equals(Object obj) {
        Location latLng = (Location) obj;

        return lat == latLng.getLat() && lng == latLng.getLng();
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Location(" + text + "," + lat + "," + lng + ")";
    }

}
