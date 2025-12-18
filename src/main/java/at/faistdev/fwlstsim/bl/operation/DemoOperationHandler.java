package at.faistdev.fwlstsim.bl.service;

import at.faistdev.fwlstsim.bl.util.RandomUtil;
import at.faistdev.fwlstsim.dataaccess.cache.OperationKeywordCache;
import at.faistdev.fwlstsim.dataaccess.entities.LatLng;
import at.faistdev.fwlstsim.dataaccess.entities.Operation;
import at.faistdev.fwlstsim.dataaccess.entities.OperationKeyword;
import at.faistdev.fwlstsim.dataaccess.entities.OperationResource;
import at.faistdev.fwlstsim.dataaccess.tasks.FirstVehicleArrivedTask;
import java.util.HashSet;
import java.util.Set;

public class DemoOperationHandler extends OperationHandler {

    @Override
    public Operation requestOperation() {
        OperationKeyword keyword = getRandomOperationKeyword();
        Operation operation = requestDemoOperation(keyword);
        return operation;
    }

    private OperationKeyword getRandomOperationKeyword() {
        OperationKeywordCache cache = OperationKeywordCache.getCache();
        int idx = RandomUtil.getInt(0, cache.size());
        return cache.get(idx);
    }

    private Operation requestDemoOperation(OperationKeyword keyword) {
        long id = 1;
        String callText = "Hallo, mir ist mein Auto in den Graben gerutscht und ich komme nicht mehr raus.";
        LatLng location = new LatLng(0, 0);
        Set<OperationResource> resources = new HashSet<>();

        Operation operation = new Operation(id, callText, location, keyword, resources);
        addDemoTasksToOperation(operation);
        return operation;
    }

    private void addDemoTasksToOperation(Operation operation) {
        operation.addTask(new FirstVehicleArrivedTask(operation, "Einsatzsofortmeldung: PKW in Graben, keine Verletzten."));
    }
}
