package at.faistdev.fwlstsim.dataaccess.entities;

public class LatLngLocation extends Location {

    private final double lat;
    private final double lng;

    public LatLngLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public Location getCopy() {
        return new LatLngLocation(lat, lng);
    }

    @Override
    public boolean equals(Object obj) {
        LatLngLocation latLng = (LatLngLocation) obj;

        return lat == latLng.getLat() && lng == latLng.getLng();
    }

}
