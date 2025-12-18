package at.faistdev.fwlstsim.dataaccess.tasks;

import at.faistdev.fwlstsim.dataaccess.entities.Operation;

public abstract class OperationTask {

    public abstract boolean isReadyToExecute(Operation operation);

    public abstract void execute(Operation operation);

    public abstract boolean isFinished(Operation operation);
}
