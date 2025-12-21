package at.faistdev.fwlstsim.dataaccess.entities;

public enum VehicleStatus {
    STATUS_3("3"), STATUS_6("6"), STATUS_9("9");

    private final String text;

    private VehicleStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
