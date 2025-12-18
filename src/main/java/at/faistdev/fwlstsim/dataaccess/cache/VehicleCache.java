package at.faistdev.fwlstsim.dataaccess.cache;

import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;

public class VehicleCache extends SingleListCache<Vehicle> {

    private static VehicleCache INSTANCE;

    public static VehicleCache getCache() {
        if (INSTANCE == null) {
            INSTANCE = new VehicleCache();
        }

        return INSTANCE;
    }

}
