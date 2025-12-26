package at.faistdev.fwlstsim.dataaccess.tasks;

import at.faistdev.fwlstsim.dataaccess.entities.Operation;
import at.faistdev.fwlstsim.dataaccess.entities.OperationStatus;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import at.faistdev.fwlstsim.ui.DispatchUi;
import at.faistdev.fwlstsim.ui.UiRegistry;
import java.util.Iterator;
import java.util.Set;

public class OperationFinishedTask extends OperationTask {

    @Override
    public boolean isReadyToExecute(Operation operation) {
        return operation.getProgressInPercent() == 100;
    }

    @Override
    public void execute(Operation operation) {
        Set<Vehicle> vehicles = operation.getVehicles();

        Iterator<Vehicle> iterator = vehicles.iterator();
        while (iterator.hasNext()) {
            Vehicle vehicle = iterator.next();
            operation.removeVehicle(vehicle);
            vehicle.sendHome();
        }

        operation.setStatus(OperationStatus.FINISHED);
        UiRegistry.get(DispatchUi.class).setSelectedOperation(null);
    }

    @Override
    public boolean isFinished(Operation operation) {
        return true;
    }

}
