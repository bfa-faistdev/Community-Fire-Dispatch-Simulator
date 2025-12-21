package at.faistdev.fwlstsim.dataaccess.entities;

public class LatLngLocation extends Location {

    private final String text;
    private final double lat;
    private final double lng;

    public LatLngLocation(String text, double lat, double lng) {
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

    @Override
    public Location getCopy() {
        return new LatLngLocation(text, lat, lng);
    }

    @Override
    public boolean equals(Object obj) {
        LatLngLocation latLng = (LatLngLocation) obj;

        return lat == latLng.getLat() && lng == latLng.getLng();
    }

    @Override
    public String getText() {
        return text;
    }

}
