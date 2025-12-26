package at.faistdev.fwlstsim.ui.events;

import at.faistdev.fwlstsim.dataaccess.entities.Operation;
import at.faistdev.fwlstsim.ui.DispatchUi;
import at.faistdev.fwlstsim.ui.UiRegistry;

public class OperationFinishedUiEvent extends UiEvent {

    private final Operation finishedOperation;

    public OperationFinishedUiEvent(Operation finishedOperation) {
        this.finishedOperation = finishedOperation;
    }

    public Operation getFinishedOperation() {
        return finishedOperation;
    }

    @Override
    public void notifyUi() {
        UiRegistry.get(DispatchUi.class).setFinishedOperation(finishedOperation);
    }

}
