package at.faistdev.fwlstsim.dataaccess.loader;

import at.faistdev.fwlstsim.dataaccess.cache.OperationKeywordCache;
import at.faistdev.fwlstsim.dataaccess.cache.VehicleCache;
import at.faistdev.fwlstsim.dataaccess.entities.LatLngLocation;
import at.faistdev.fwlstsim.dataaccess.entities.OperationKeyword;
import at.faistdev.fwlstsim.dataaccess.entities.OperationResource;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import at.faistdev.fwlstsim.dataaccess.entities.VehicleHome;
import java.util.List;

public class DemoDataLoader extends PersistentDataLoader {

    @Override
    public void loadDataIntoCache() {
        loadOperationKeywords();
        loadVehicles();
    }

    private void loadOperationKeywords() {
        {
            OperationKeyword operationKeyword = new OperationKeyword("T03-VU-Berg.Öl.");
            OperationKeywordCache.getCache().add(operationKeyword);
        }
        {
            OperationKeyword operationKeyword = new OperationKeyword("T03-VU-mit-Verl.");
            OperationKeywordCache.getCache().add(operationKeyword);
        }
    }

    private void loadVehicles() {
        VehicleHome vehicleHome = new VehicleHome("FF Blumegg Teipl", new LatLngLocation("Hauptstraße 64, 8502 Lannach", 0, 0));

        {
            Vehicle vehicle = new Vehicle("TLF 1000 Blumegg Teipl", vehicleHome, List.of(OperationResource.CHAINSAW));
            VehicleCache.getCache().add(vehicle);
        }
        {
            Vehicle vehicle = new Vehicle("LFB-A Blumegg Teipl", vehicleHome, List.of(OperationResource.CHAINSAW));
            VehicleCache.getCache().add(vehicle);
        }
    }

}
