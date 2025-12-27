package at.faistdev.fwlstsim.dataaccess.entities;

import java.util.Set;

public class PossibleOperation {

    private final OperationKeyword keyword;
    private final String callingNumber;
    private final String callText;
    private final String initalRadioMessage;
    private final Set<OperationResource> requiredResources;

    public PossibleOperation(OperationKeyword keyword, String callingNumber, String callText, String initalRadioMessage, Set<OperationResource> requiredResources) {
        this.keyword = keyword;
        this.callingNumber = callingNumber;
        this.callText = callText;
        this.initalRadioMessage = initalRadioMessage;
        this.requiredResources = requiredResources;
    }

    public OperationKeyword getKeyword() {
        return keyword;
    }

    public String getCallingNumber() {
        return callingNumber;
    }

    public String getCallText() {
        return callText;
    }

    public String getInitalRadioMessage() {
        return initalRadioMessage;
    }

    public Set getRequiredResources() {
        return requiredResources;
    }

}
