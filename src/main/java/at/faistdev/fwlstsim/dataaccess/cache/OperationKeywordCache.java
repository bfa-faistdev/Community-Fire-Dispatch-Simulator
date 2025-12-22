package at.faistdev.fwlstsim.dataaccess.cache;

import at.faistdev.fwlstsim.dataaccess.entities.OperationKeyword;
import java.util.ArrayList;

public class OperationKeywordCache extends SingleListCache<OperationKeyword> {

    private static OperationKeywordCache INSTANCE;

    public static OperationKeywordCache getCache() {
        if (INSTANCE == null) {
            INSTANCE = new OperationKeywordCache();
        }

        return INSTANCE;
    }

    public OperationKeyword getByName(String name) {
        ArrayList<OperationKeyword> all = getAll();
        for (OperationKeyword keyword : all) {
            if (keyword.getName().equals(name)) {
                return keyword;
            }
        }

        return null;
    }

}
