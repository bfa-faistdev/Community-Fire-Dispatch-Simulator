package at.faistdev.fwlstsim.dataaccess.tasks;

import at.faistdev.fwlstsim.bl.service.OperationService;
import at.faistdev.fwlstsim.dataaccess.entities.Operation;
import at.faistdev.fwlstsim.dataaccess.entities.OperationResource;
import at.faistdev.fwlstsim.dataaccess.entities.RadioMessage;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import java.util.Set;

public class FirstVehicleArrivedTask extends OperationTask {

    private final Operation operation;

    private final String initialRadioMessageText;

    public FirstVehicleArrivedTask(Operation operation, String initialRadioMessageText) {
        this.operation = operation;
        this.initialRadioMessageText = initialRadioMessageText;
    }

    @Override
    public boolean isReadyToExecute() {
        return OperationService.isVehicleOnSite(operation);
    }

    @Override
    public void execute() {
        String message = initialRadioMessageText;

        if (OperationService.isNeedToRequestAdditionalResources(operation)) {
            message += " Weitere Resourcen an der Einsatzstelle benötigt: ";
            Set<OperationResource> additionalNeededResources = OperationService.getAdditionalNeededResources(operation);
            for (OperationResource resource : additionalNeededResources) {
                message += resource.getText() + ", ";
            }
        } else {
            message += " Keine weiteren Kräfte benötigt.";
        }

        Vehicle leadVehicle = OperationService.getLeadVehicleOnSite(operation);
        leadVehicle.addRadioMessage(new RadioMessage(message));
    }

}
