package at.faistdev.fwlstsim.bl.service;

import at.faistdev.fwlstsim.bl.util.DurationUtil;
import at.faistdev.fwlstsim.bl.util.RandomUtil;
import at.faistdev.fwlstsim.dataaccess.cache.PossibleOperationLocationCache;
import at.faistdev.fwlstsim.dataaccess.cache.PossibleOperationsCache;
import at.faistdev.fwlstsim.dataaccess.entities.Location;
import at.faistdev.fwlstsim.dataaccess.entities.Operation;
import at.faistdev.fwlstsim.dataaccess.entities.PossibleOperation;
import at.faistdev.fwlstsim.dataaccess.tasks.FirstVehicleArrivedTask;
import at.faistdev.fwlstsim.dataaccess.tasks.OperationFinishedTask;
import at.faistdev.fwlstsim.dataaccess.tasks.UpdateProgressTask;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CachedOperationHandler extends OperationHandler {

    @Override
    public Operation requestOperation() {
        PossibleOperation possibleOperation = getRandomPossibleOperation();
        Location location = getRandomOperationLocation();

        Operation operation = new Operation(getNextId(), possibleOperation.getCallText(), possibleOperation.getCallingNumber(), location, possibleOperation.getRequiredResources(),
                DurationUtil.getMinutes(10));

        addTasksToOperation(operation, possibleOperation.getInitalRadioMessage());

        return operation;
    }

    private PossibleOperation getRandomPossibleOperation() {
        ArrayList<PossibleOperation> allPossibleOperations = PossibleOperationsCache.getCache().getAll();
        int randomIdx = RandomUtil.getInt(0, allPossibleOperations.size() - 1);
        return allPossibleOperations.get(randomIdx);
    }

    private Location getRandomOperationLocation() {
        List<Location> locationsIn8502Or8503 = PossibleOperationLocationCache.getCache().getAll().stream()//
                .filter(loc -> loc.getPostalCode().equals("8502") || loc.getPostalCode().equals("8503"))//
                .collect(Collectors.toList());

        int randomIdx = RandomUtil.getInt(0, locationsIn8502Or8503.size() - 1);
        return locationsIn8502Or8503.get(randomIdx);
    }

    private void addTasksToOperation(Operation operation, String initialRadioMessage) {
        operation.addTask(new FirstVehicleArrivedTask(initialRadioMessage));
        operation.addTask(new UpdateProgressTask());
        operation.addTask(new OperationFinishedTask());
    }
}
