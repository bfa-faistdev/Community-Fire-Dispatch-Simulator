package at.faistdev.fwlstsim.dataaccess.tasks;

import at.faistdev.fwlstsim.bl.service.OperationService;
import at.faistdev.fwlstsim.dataaccess.entities.Operation;

public class UpdateProgressTask extends OperationTask {

    @Override
    public boolean isReadyToExecute(Operation operation) {
        boolean resourceRequiredOnSite = OperationService.isResourceRequiredOnSite(operation);
        boolean allResourcesOnSite = resourceRequiredOnSite == false;

        return allResourcesOnSite;
    }

    @Override
    public void execute(Operation operation) {
        operation.increaseProgressInTicks();
    }

    @Override
    public boolean isFinished(Operation operation) {
        return operation.getProgressInTicks() >= operation.getDurationAfterAllResourcesOnSiteInTicks();
    }

}
