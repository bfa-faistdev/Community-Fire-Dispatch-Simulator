package at.faistdev.fwlstsim.bl.game;

import at.faistdev.fwlstsim.bl.routing.RoutingService;
import at.faistdev.fwlstsim.bl.service.OperationService;
import at.faistdev.fwlstsim.dataaccess.cache.OperationCache;
import at.faistdev.fwlstsim.dataaccess.cache.VehicleCache;
import at.faistdev.fwlstsim.dataaccess.entities.Operation;
import at.faistdev.fwlstsim.dataaccess.entities.OperationStatus;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import at.faistdev.fwlstsim.dataaccess.tasks.OperationTask;
import at.faistdev.fwlstsim.ui.events.TickUpdateUiEvent;
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
            if (GameProperties.SPEED == 0) {
                System.out.println("Game is paused");
                sleep();
                continue;
            }

            requestOperationIfDemand();
            doRoutingAndStatusUpdates();

            List<Operation> operations = OperationCache.getCache().getAll();
            for (Operation operation : operations) {
                executeNextTask(operation);
            }

            currentTick++;
            new TickUpdateUiEvent(currentTick).notifyUi();
            System.out.println("Current Tick = " + currentTick);
            sleep();
        }
    }

    private void sleep() {
        try {
            long msToSleep = getMsToSleep();
            Thread.sleep(msToSleep);
        } catch (InterruptedException ex) {
            System.getLogger(Game.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private long getMsToSleep() {
        int speed = GameProperties.SPEED;
        if (speed == 0) {
            return 1000;
        }

        return 1000 / GameProperties.SPEED;
    }

    private void requestOperationIfDemand() {
        long activeOperations = getNumberOfCurrentActiveOperations();
        if (activeOperations < GameProperties.MAX_ACTIVE_OPERATIONS) {
            OperationService.requestOperation(GameProperties.OPERATION_HANDLER);
        }
    }

    private long getNumberOfCurrentActiveOperations() {
        return OperationCache.getCache().getAll().stream().filter(operation -> operation.getStatus() != OperationStatus.FINISHED).count();
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
