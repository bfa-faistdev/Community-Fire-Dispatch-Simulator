package at.faistdev.fwlstsim.dataaccess.loader;

import at.faistdev.fwlstsim.dataaccess.cache.VehicleCache;
import at.faistdev.fwlstsim.dataaccess.entities.OperationResource;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import java.util.List;

public class DemoDataLoader extends PersistentDataLoader {

    @Override
    public void loadDataIntoCache() {
        loadDemoVehicles();
    }

    private void loadDemoVehicles() {
        Vehicle vehicle = new Vehicle(List.of(OperationResource.CHAINSAW));
        VehicleCache.getCache().add(vehicle);
    }

}
