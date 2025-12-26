package at.faistdev.fwlstsim.dataaccess.cache;

import at.faistdev.fwlstsim.dataaccess.entities.Location;

public class PossibleOperationLocationCache extends SingleListCache<Location> {

    private static PossibleOperationLocationCache INSTANCE;

    public static PossibleOperationLocationCache getCache() {
        if (INSTANCE == null) {
            INSTANCE = new PossibleOperationLocationCache();
        }

        return INSTANCE;
    }
}
