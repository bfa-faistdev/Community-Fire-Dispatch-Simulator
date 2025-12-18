package at.faistdev.fwlstsim.bl.game;

import at.faistdev.fwlstsim.bl.service.OperationService;
import at.faistdev.fwlstsim.bl.service.RoutingService;
import at.faistdev.fwlstsim.dataaccess.cache.OperationCache;
import at.faistdev.fwlstsim.dataaccess.cache.VehicleCache;
import at.faistdev.fwlstsim.dataaccess.entities.Operation;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import at.faistdev.fwlstsim.dataaccess.tasks.OperationTask;
import java.util.ArrayList;
import java.util.List;

public class Game implements Runnable {

    private long currentTick = 1;

    @Override
    public void run() {
        requestOperationIfDemand();

        List<Operation> operations = OperationCache.getCache().getAll();
        for (Operation operation : operations) {
            doRoutingAndStatusUpdates();

            executeNextTask(operation);

            // ToDo: Update progress
            // ToDo: Is Operation finished --> Send vehicles home
        }
    }

    private void requestOperationIfDemand() {
        int activeOperations = getNumberOfCurrentActiveOperations();
        if (activeOperations < GameProperties.MAX_ACTIVE_OPERATIONS) {
            OperationService.requestOperation(GameProperties.OPERATION_HANDLER);
        }
    }

    private int getNumberOfCurrentActiveOperations() {
        return OperationCache.getCache().size();
    }

    private void executeNextTask(Operation operation) {
        OperationTask task = operation.getNextTask();
        if (task == null) {
            return;
        }

        if (task.isReadyToExecute() == false) {
            return;
        }

        task.execute();

        operation.removeTask(task);
    }

    private void doRoutingAndStatusUpdates() {
        ArrayList<Vehicle> vehicles = VehicleCache.getCache().getAll();
        for (Vehicle vehicle : vehicles) {
            RoutingService.doRouting(currentTick, vehicle);
        }
    }
}
