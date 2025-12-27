package at.faistdev.fwlstsim.dataaccess.loader;

import at.faistdev.fwlstsim.dataaccess.cache.OperationKeywordCache;
import at.faistdev.fwlstsim.dataaccess.cache.PossibleOperationLocationCache;
import at.faistdev.fwlstsim.dataaccess.cache.PossibleOperationsCache;
import at.faistdev.fwlstsim.dataaccess.cache.VehicleCache;
import at.faistdev.fwlstsim.dataaccess.entities.Location;
import at.faistdev.fwlstsim.dataaccess.entities.OperationKeyword;
import at.faistdev.fwlstsim.dataaccess.entities.OperationResource;
import at.faistdev.fwlstsim.dataaccess.entities.PossibleOperation;
import at.faistdev.fwlstsim.dataaccess.entities.Vehicle;
import at.faistdev.fwlstsim.dataaccess.entities.VehicleHome;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DemoDataLoader extends PersistentDataLoader {

    @Override
    public void loadDataIntoCache() {
        loadOperationKeywords();
        loadVehicles();
        loadPossibleOperationLocations();
        loadPossibleOperations();
    }

    private void loadOperationKeywords() {
        List<String> keywordNames = List.of("T02-Türöffnung", "T03-VU-Berg.Öl.", "T03-VU-mit-Verl.", "T04-Pumparbeiten", "T05-Insektenbekämpfung", "T07-Unwetter", "T08-Tierrettung", "T10-VU-eingeklemmt",
                "T11-Menschenrettung", "T12-Busunfall", "T12-Bahn", "T16-Flugunfall", "T17-Schadstoff");

        for (String keywordName : keywordNames) {
            OperationKeyword operationKeyword = new OperationKeyword(keywordName);
            OperationKeywordCache.getCache().add(operationKeyword);
        }
    }

    private void loadVehicles() {
        VehicleHome vehicleHome = new VehicleHome("FF Blumegg Teipl", new Location("Hauptstraße", "64", "8502", "Lannach", 46.939554, 15.317044));

        {
            Vehicle vehicle = new Vehicle("TLF 1000 Blumegg Teipl", vehicleHome, List.of(OperationResource.SMALL_WATER_TANK_LESS_THAN_1_000_L, OperationResource.BIG_WATER_TANK_MORE_THAN_1_000_L,
                    OperationResource.FIRE_PUMP, OperationResource.LADDER, OperationResource.LIGHTING, OperationResource.VENTILATION, OperationResource.WATER_DAMAGE_KIT));
            VehicleCache.getCache().add(vehicle);
        }
        {
            Vehicle vehicle = new Vehicle("LFB-A Blumegg Teipl", vehicleHome, List.of(OperationResource.CHAINSAW, OperationResource.WINCH, OperationResource.SMALL_WATER_TANK_LESS_THAN_1_000_L,
                    OperationResource.DOOR_OPENING_KIT, OperationResource.FIRE_PUMP, OperationResource.HYDRAULIC_SPREADER, OperationResource.LADDER, OperationResource.LIGHTING,
                    OperationResource.OIL_SPILL, OperationResource.SPINE_BOARD, OperationResource.WATER_DAMAGE_KIT, OperationResource.INSECT_KIT));
            VehicleCache.getCache().add(vehicle);
        }
    }

    private void loadPossibleOperationLocations() {
        URL url = DemoDataLoader.class.getResource("/at/faistdev/fwlstsim/dataaccess/loader/addresses/Deutschlandsberg.json");
        try {
            ArrayList<Location> locations = AddressDataLoaderUtil.getLocations(url.toURI());
            System.out.println("Loaded " + locations.size() + " locations");
            PossibleOperationLocationCache.getCache().addAll(locations);
        } catch (URISyntaxException ex) {
            System.getLogger(DemoDataLoader.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private void loadPossibleOperations() {
        URL url = DemoDataLoader.class.getResource("/at/faistdev/fwlstsim/bl/operations/DemoOperations.json");
        try {
            ArrayList<PossibleOperation> operations = OperationDataLoaderUtil.getOperations(url.toURI());
            System.out.println("Loaded " + operations.size() + " operations");
            PossibleOperationsCache.getCache().addAll(operations);
        } catch (URISyntaxException ex) {
            System.getLogger(DemoDataLoader.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

}
