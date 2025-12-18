package at.faistdev.fwlstsim.dataaccess.cache;

import at.faistdev.fwlstsim.dataaccess.entities.OperationKeyword;

public class OperationKeywordCache extends SingleListCache<OperationKeyword> {

    private static OperationKeywordCache INSTANCE;

    public static OperationKeywordCache getCache() {
        if (INSTANCE == null) {
            INSTANCE = new OperationKeywordCache();
        }

        return INSTANCE;
    }

}
