package at.faistdev.fwlstsim.bl.game;

import at.faistdev.fwlstsim.bl.service.DemoOperationHandler;
import at.faistdev.fwlstsim.bl.service.OperationHandler;

public class GameProperties {

    public static int SPEED = 1;
    public static int MAX_ACTIVE_OPERATIONS = 1;
    public static OperationHandler OPERATION_HANDLER = new DemoOperationHandler();
}
