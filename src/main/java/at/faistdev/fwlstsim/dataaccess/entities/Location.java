package at.faistdev.fwlstsim.dataaccess.entities;

public class Location {

    private final String street;
    private final String housenumber;
    private final String postalCode;
    private final String city;
    private final double lat;
    private final double lng;

    public Location(String street, String housenumber, String postalCode, String city, double lat, double lng) {
        this.street = street;
        this.housenumber = housenumber;
        this.postalCode = postalCode;
        this.city = city;
        this.lat = lat;
        this.lng = lng;
    }

    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
        this.street = "";
        this.city = "";
        this.housenumber = "";
        this.postalCode = "";
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public Location getCopy() {
        return new Location(street, housenumber, postalCode, city, lat, lng);
    }

    public boolean equals(Object obj) {
        Location latLng = (Location) obj;

        return lat == latLng.getLat() && lng == latLng.getLng();
    }

    public String getText() {
        return street + " " + housenumber + ", " + postalCode + " " + city;
    }

    @Override
    public String toString() {
        return "Location(" + getText() + "," + lat + "," + lng + ")";
    }

    public String getStreet() {
        return street;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

}
