package at.faistdev.fwlstsim.bl.service;

import at.faistdev.fwlstsim.dataaccess.cache.OperationCache;
import at.faistdev.fwlstsim.dataaccess.entities.Operation;
import at.faistdev.fwlstsim.dataaccess.entities.OperationResource;
import at.faistdev.fwlstsim.dataaccess.entities.VehicleStatus;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OperationService {

    public static Operation requestOperation(OperationHandler handler) {
        Operation operation = handler.requestOperation();
        OperationCache.getCache().add(operation);
        return operation;
    }

    public static boolean isVehicleOnSite(Operation operation) {
        return operation.getVehicles().stream().filter(v -> v.getStatus() == VehicleStatus.STATUS_6).count() > 0;
    }

    public static Set<OperationResource> getAdditionalNeededResources(Operation operation) {
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

    public static boolean isResourceRequired(Operation operation) {
        return getAdditionalNeededResources(operation).size() > 0;
    }

    private static Set<OperationResource> getDispatchedResources(Operation operation) {
        return operation.getVehicles().stream().flatMap(v -> v.getResources().stream()).collect(Collectors.toSet());
    }

    public static boolean isNeedToRequestAdditionalResources(Operation operation) {
        return isVehicleOnSite(operation) && operation.getLastResourceRequest() == 0
                && isResourceRequired(operation);
    }
}
