package at.faistdev.fwlstsim.ui.components;

import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import javax.swing.JPanel;

public class VehiclePanel extends JPanel {

    private final Vehicle vehicle;

    public VehiclePanel(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

}
