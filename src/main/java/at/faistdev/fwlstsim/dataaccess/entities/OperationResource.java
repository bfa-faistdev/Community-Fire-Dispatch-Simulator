package at.faistdev.fwlstsim.dataaccess.entities;

public enum OperationResource {

    CHAINSAW("Kettensäge"),
    WATER_TANK_VEHICLE("Tanklöschfahrzeug"),
    WINCH("Seilwinde");

    private final String text;

    private OperationResource(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
