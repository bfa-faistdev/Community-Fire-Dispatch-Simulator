package at.faistdev.fwlstsim.bl.game;

import at.faistdev.fwlstsim.bl.routing.RoutingService;
import at.faistdev.fwlstsim.bl.service.OperationService;
import at.faistdev.fwlstsim.dataaccess.cache.OperationCache;
import at.faistdev.fwlstsim.dataaccess.cache.VehicleCache;
import at.faistdev.fwlstsim.dataaccess.entities.Operation;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import at.faistdev.fwlstsim.dataaccess.tasks.OperationTask;
import java.util.ArrayList;
import java.util.List;

public class Game implements Runnable {

    private long currentTick = 1;

    public void init() {
        GameProperties.DATA_LOADER.loadDataIntoCache();
    }

    @Override
    public void run() {
        while (true) {
            requestOperationIfDemand();
            doRoutingAndStatusUpdates();

            List<Operation> operations = OperationCache.getCache().getAll();
            for (Operation operation : operations) {
                executeNextTask(operation);
            }

            currentTick++;
            System.out.println("Current Tick = " + currentTick);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.getLogger(Game.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
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

        if (task.isReadyToExecute(operation) == false) {
            return;
        }

        System.out.println("Executing " + task.getClass().getSimpleName());
        task.execute(operation);

        if (task.isFinished(operation)) {
            System.out.println("Finished " + task.getClass().getSimpleName());
            operation.removeTask(task);
        }
    }

    private void doRoutingAndStatusUpdates() {
        ArrayList<Vehicle> vehicles = VehicleCache.getCache().getAll();
        for (Vehicle vehicle : vehicles) {
            RoutingService.doRouting(currentTick, vehicle);
        }
    }

}
