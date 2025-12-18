package at.faistdev.fwlstsim.dataaccess.entities;

import at.faistdev.fwlstsim.dataaccess.tasks.OperationTask;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Operation {

    private final long id;
    private final String callText;
    private final LatLng location;
    private final OperationKeyword operationKeyword;

    private final Set<OperationResource> requiredResources;

    private final List<Vehicle> vehicles;
    private final List<RadioMessage> pendingRadioMessages;
    private final List<OperationTask> pendingTasks;

    private long lastResourceRequest = 0;

    public Operation(long id, String callText, LatLng location, OperationKeyword operationKeyword,
            Set<OperationResource> initialRequiredResources) {
        this.id = id;
        this.callText = callText;
        this.location = location;
        this.operationKeyword = operationKeyword;
        this.requiredResources = initialRequiredResources;
        this.vehicles = new ArrayList<>();
        this.pendingRadioMessages = new ArrayList<>();
        this.pendingTasks = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getCallText() {
        return callText;
    }

    public LatLng getLocation() {
        return location;
    }

    public OperationKeyword getOperationKeyword() {
        return operationKeyword;
    }

    public Set<OperationResource> getRequiredResources() {
        return requiredResources;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public long getLastResourceRequest() {
        return lastResourceRequest;
    }

    public void setLastResourceRequest(long lastResourceRequest) {
        this.lastResourceRequest = lastResourceRequest;
    }

    public void addRadioMessage(RadioMessage message) {
        pendingRadioMessages.add(message);
    }

    public RadioMessage getNextRadioMessage() {
        if (pendingRadioMessages.isEmpty()) {
            return null;
        }

        return pendingRadioMessages.get(0);
    }

    public void addTask(OperationTask task) {
        pendingTasks.add(task);
    }

    public OperationTask getNextTask() {
        if (pendingTasks.isEmpty()) {
            return null;
        }

        return pendingTasks.get(0);
    }

    public void removeTask(OperationTask task) {
        pendingTasks.remove(task);
    }
}
