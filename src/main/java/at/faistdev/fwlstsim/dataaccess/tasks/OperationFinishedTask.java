package at.faistdev.fwlstsim.dataaccess.tasks;

import at.faistdev.fwlstsim.dataaccess.entities.Operation;
import at.faistdev.fwlstsim.dataaccess.entities.OperationStatus;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import java.util.Set;

public class OperationFinishedTask extends OperationTask {

    @Override
    public boolean isReadyToExecute(Operation operation) {
        return operation.getProgressInPercent() == 100;
    }

    @Override
    public void execute(Operation operation) {
        Set<Vehicle> vehicles = operation.getVehicles();
        for (Vehicle vehicle : vehicles) {
            operation.removeVehicle(vehicle);
            vehicle.sendHome();
        }

        operation.setStatus(OperationStatus.FINISHED);
    }

    @Override
    public boolean isFinished(Operation operation) {
        return true;
    }

}
