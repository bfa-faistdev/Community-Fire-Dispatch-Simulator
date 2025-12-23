package at.faistdev.fwlstsim.dataaccess.cache;

import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import java.util.ArrayList;

public class VehicleCache extends SingleListCache<Vehicle> {

    private static VehicleCache INSTANCE;

    public static VehicleCache getCache() {
        if (INSTANCE == null) {
            INSTANCE = new VehicleCache();
        }

        return INSTANCE;
    }

    public Vehicle getByName(String name) {
        ArrayList<Vehicle> all = getAll();
        return all.stream().filter(vehicle -> vehicle.getName().equals(name)).findAny().orElse(null);
    }

}
