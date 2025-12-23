package at.faistdev.fwlstsim.bl.game;

import at.faistdev.fwlstsim.bl.service.DemoOperationHandler;
import at.faistdev.fwlstsim.bl.service.OperationHandler;
import at.faistdev.fwlstsim.dataaccess.loader.DemoDataLoader;
import at.faistdev.fwlstsim.dataaccess.loader.PersistentDataLoader;

public class GameProperties {

    public static int SPEED = 1;
    public static final int MAX_ACTIVE_OPERATIONS = 1;
    public static final OperationHandler OPERATION_HANDLER = new DemoOperationHandler();
    public static final PersistentDataLoader DATA_LOADER = new DemoDataLoader();
    public static final String NAME_OF_DISPATCH = "Florian Steiermark";

}
