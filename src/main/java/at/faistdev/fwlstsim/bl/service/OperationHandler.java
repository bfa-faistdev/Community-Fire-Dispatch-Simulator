package at.faistdev.fwlstsim.bl.service;

import at.faistdev.fwlstsim.dataaccess.cache.OperationCache;
import at.faistdev.fwlstsim.dataaccess.entities.Operation;

public abstract class OperationHandler {

    public abstract Operation requestOperation();

    protected int getNextId() {
        return OperationCache.getCache().getAll().size() + 1;
    }
}
