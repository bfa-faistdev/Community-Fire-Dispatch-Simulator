package at.faistdev.fwlstsim.dataaccess.tasks;

public abstract class OperationTask {

    public abstract boolean isReadyToExecute();

    public abstract void execute();
}
