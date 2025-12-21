package at.faistdev.fwlstsim.dataaccess.entities;

import at.faistdev.fwlstsim.dataaccess.tasks.OperationTask;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Operation {

    private final long id;
    private final String callingNumber;
    private final String callText;
    private final Location location;
    private final OperationKeyword operationKeyword;
    private final long durationAfterAllResourcesOnSiteInTicks;

    private final Set<OperationResource> requiredResources;

    private final Set<Vehicle> vehicles;
    private final List<OperationTask> pendingTasks;

    private long lastResourceRequest = 0;
    private long progressInTicks = 0;
    private OperationStatus status = OperationStatus.INITAL;

    public Operation(long id, String callText, String callingNumber, Location location, OperationKeyword operationKeyword,
            Set<OperationResource> initialRequiredResources, long durationAfterAllResourcesOnSiteInTicks) {
        this.id = id;
        this.callText = callText;
        this.callingNumber = callingNumber;
        this.location = location;
        this.operationKeyword = operationKeyword;
        this.requiredResources = initialRequiredResources;
        this.vehicles = new HashSet<>();
        this.pendingTasks = new ArrayList<>();
        this.durationAfterAllResourcesOnSiteInTicks = durationAfterAllResourcesOnSiteInTicks;
    }

    public long getId() {
        return id;
    }

    public String getCallText() {
        return callText;
    }

    public Location getLocation() {
        return location;
    }

    public OperationKeyword getOperationKeyword() {
        return operationKeyword;
    }

    public Set<OperationResource> getRequiredResources() {
        return requiredResources;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public long getLastResourceRequest() {
        return lastResourceRequest;
    }

    public void setLastResourceRequest(long lastResourceRequest) {
        this.lastResourceRequest = lastResourceRequest;
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

    public long getDurationAfterAllResourcesOnSiteInTicks() {
        return durationAfterAllResourcesOnSiteInTicks;
    }

    public void increaseProgressInTicks() {
        progressInTicks++;
    }

    public long getProgressInTicks() {
        return progressInTicks;
    }

    public int getProgressInPercent() {
        int progress = (int) (progressInTicks / durationAfterAllResourcesOnSiteInTicks);

        if (progress > 100) {
            progress = 100;
        }

        return progress;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    public String getCallingNumber() {
        return callingNumber;
    }

}
