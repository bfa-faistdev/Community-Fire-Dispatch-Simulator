package at.faistdev.fwlstsim.bl.service;

import at.faistdev.fwlstsim.dataaccess.cache.OperationCache;
import at.faistdev.fwlstsim.dataaccess.entities.Operation;
import at.faistdev.fwlstsim.dataaccess.entities.OperationResource;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import at.faistdev.fwlstsim.dataaccess.entities.VehicleStatus;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OperationService {

    public static Operation requestOperation(OperationHandler handler) {
        Operation operation = handler.requestOperation();
        OperationCache.getCache().add(operation);
        return operation;
    }

    public static boolean isVehicleOnSite(Operation operation) {
        return getVehiclesOnSite(operation).size() > 0;
    }

    public static List<Vehicle> getVehiclesOnSite(Operation operation) {
        return operation.getVehicles().stream()//
                .filter(v -> v.getStatus() == VehicleStatus.STATUS_6)//
                .collect(Collectors.toList());
    }

    public static Set<OperationResource> getAdditionalNeededResourcesDispatched(Operation operation) {
        Set<OperationResource> required = operation.getRequiredResources();
        Set<OperationResource> dispatched = getDispatchedResources(operation);
        Set<OperationResource> additionalNeeded = new HashSet<>();

        for (OperationResource requiredResource : required) {
            if (!dispatched.contains(requiredResource)) {
                additionalNeeded.add(requiredResource);
            }
        }

        return additionalNeeded;
    }

    public static boolean isResourceRequiredDispatched(Operation operation) {
        return getAdditionalNeededResourcesDispatched(operation).size() > 0;
    }

    public static Set<OperationResource> getDispatchedResources(Operation operation) {
        return operation.getVehicles().stream().flatMap(v -> v.getResources().stream()).collect(Collectors.toSet());
    }

    public static boolean isNeedToRequestAdditionalResources(Operation operation) {
        return isVehicleOnSite(operation) && operation.getLastResourceRequest() == 0
                && isResourceRequiredDispatched(operation);
    }

    public static Vehicle getLeadVehicleOnSite(Operation operation) {
        List<Vehicle> vehiclesOnSite = getVehiclesOnSite(operation);
        if (vehiclesOnSite.isEmpty()) {
            return null;
        }

        return vehiclesOnSite.get(0);
    }

    public static Set<OperationResource> getOnSiteResources(Operation operation) {
        return operation.getVehicles().stream()//
                .filter(v -> v.getStatus() == VehicleStatus.STATUS_3)//
                .flatMap(v -> v.getResources().stream())//
                .collect(Collectors.toSet());
    }

    public static Set<OperationResource> getAdditionalNeededResourcesOnSite(Operation operation) {
        Set<OperationResource> required = operation.getRequiredResources();
        Set<OperationResource> onSite = getOnSiteResources(operation);
        Set<OperationResource> additionalNeeded = new HashSet<>();

        for (OperationResource requiredResource : required) {
            if (!onSite.contains(requiredResource)) {
                additionalNeeded.add(requiredResource);
            }
        }

        return additionalNeeded;
    }

    public static boolean isResourceRequiredOnSite(Operation operation) {
        return getAdditionalNeededResourcesOnSite(operation).size() > 0;
    }

    public static boolean isVehicleDispatched(Vehicle vehicle) {
        ArrayList<Operation> allOperations = OperationCache.getCache().getAll();
        for (Operation operation : allOperations) {
            List<Vehicle> dispatchedVehicles = operation.getVehicles();
            if (dispatchedVehicles.contains(vehicle)) {
                return true;
            }
        }

        return false;
    }
}
