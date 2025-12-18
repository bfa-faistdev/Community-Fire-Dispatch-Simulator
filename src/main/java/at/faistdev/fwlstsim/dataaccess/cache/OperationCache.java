package at.faistdev.fwlstsim.dataaccess.cache;

import at.faistdev.fwlstsim.dataaccess.entities.Operation;

public class OperationCache extends SingleListCache<Operation> {

    private static OperationCache INSTANCE;

    public static OperationCache getCache() {
        if (INSTANCE == null) {
            INSTANCE = new OperationCache();
        }

        return INSTANCE;
    }

}
