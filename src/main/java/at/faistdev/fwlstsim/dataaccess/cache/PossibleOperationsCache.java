package at.faistdev.fwlstsim.dataaccess.cache;

import at.faistdev.fwlstsim.dataaccess.entities.PossibleOperation;

public class PossibleOperationsCache extends SingleListCache<PossibleOperation> {

    private static PossibleOperationsCache INSTANCE;

    public static PossibleOperationsCache getCache() {
        if (INSTANCE == null) {
            INSTANCE = new PossibleOperationsCache();
        }

        return INSTANCE;
    }
}
