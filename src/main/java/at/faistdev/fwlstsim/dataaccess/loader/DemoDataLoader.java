package at.faistdev.fwlstsim.dataaccess.loader;

import at.faistdev.fwlstsim.dataaccess.cache.VehicleCache;
import at.faistdev.fwlstsim.dataaccess.entities.LatLngLocation;
import at.faistdev.fwlstsim.dataaccess.entities.OperationResource;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import at.faistdev.fwlstsim.dataaccess.entities.VehicleHome;
import java.util.List;

public class DemoDataLoader extends PersistentDataLoader {

    @Override
    public void loadDataIntoCache() {
        loadDemoVehicles();
    }

    private void loadDemoVehicles() {
        VehicleHome vehicleHome = new VehicleHome("FF Demo", new LatLngLocation(0, 0));

        Vehicle vehicle = new Vehicle(vehicleHome, List.of(OperationResource.CHAINSAW));
        VehicleCache.getCache().add(vehicle);
    }

}
