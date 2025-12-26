package at.faistdev.fwlstsim.bl.service;

import at.faistdev.fwlstsim.bl.util.DurationUtil;
import at.faistdev.fwlstsim.bl.util.RandomUtil;
import at.faistdev.fwlstsim.dataaccess.cache.OperationKeywordCache;
import at.faistdev.fwlstsim.dataaccess.cache.PossibleOperationLocationCache;
import at.faistdev.fwlstsim.dataaccess.entities.Location;
import at.faistdev.fwlstsim.dataaccess.entities.Operation;
import at.faistdev.fwlstsim.dataaccess.entities.OperationKeyword;
import at.faistdev.fwlstsim.dataaccess.entities.OperationResource;
import at.faistdev.fwlstsim.dataaccess.tasks.FirstVehicleArrivedTask;
import at.faistdev.fwlstsim.dataaccess.tasks.OperationFinishedTask;
import at.faistdev.fwlstsim.dataaccess.tasks.UpdateProgressTask;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        String callingNumber = "+43 664 960 2211";
        // Location location = new Location("Sankt Josefer Straße, 8502 Lannach", 46.926147, 15.310162);
        Location location = getRandomOperationLocation();
        Set<OperationResource> resources = Set.of(OperationResource.WINCH);
        long duration = DurationUtil.getMinutes(45);

        Operation operation = new Operation(id, callText, callingNumber, location, resources, duration);
        addDemoTasksToOperation(operation);
        return operation;
    }

    private void addDemoTasksToOperation(Operation operation) {
        operation.addTask(new FirstVehicleArrivedTask("Einsatzsofortmeldung: PKW in Graben, keine Verletzten."));
        operation.addTask(new UpdateProgressTask());
        operation.addTask(new OperationFinishedTask());
    }

    private Location getRandomOperationLocation() {
        List<Location> locationsIn8502Or8503 = PossibleOperationLocationCache.getCache().getAll().stream()//
                .filter(loc -> loc.getPostalCode().equals("8502") || loc.getPostalCode().equals("8503"))//
                .collect(Collectors.toList());

        int randomIdx = RandomUtil.getInt(0, locationsIn8502Or8503.size() - 1);
        return locationsIn8502Or8503.get(randomIdx);
    }
}
