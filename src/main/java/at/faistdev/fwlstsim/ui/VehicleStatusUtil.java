package at.faistdev.fwlstsim.ui;

import at.faistdev.fwlstsim.dataaccess.entities.VehicleStatus;
import java.awt.Color;

public class VehicleStatusUtil {

    public static Color getColor(VehicleStatus status) {
        switch (status) {
            case STATUS_3:
                return Color.yellow;
            case STATUS_6:
                return Color.red;
            case STATUS_9:
                return Color.white;
        }

        return Color.white;
    }
}
