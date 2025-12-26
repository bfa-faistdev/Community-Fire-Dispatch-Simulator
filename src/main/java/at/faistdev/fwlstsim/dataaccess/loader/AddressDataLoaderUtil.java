package at.faistdev.fwlstsim.dataaccess.loader;

import at.faistdev.fwlstsim.bl.util.StringUtil;
import at.faistdev.fwlstsim.dataaccess.entities.Location;
import at.faistdev.fwlstsim.dataaccess.loader.addresses.Element;
import at.faistdev.fwlstsim.dataaccess.loader.addresses.OverpassResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class AddressDataLoaderUtil {

    private final static String CITY = "addr:city";
    private final static String HOUSENUMBER = "addr:housenumber";
    private final static String STREET = "addr:street";
    private final static String POSTCODE = "addr:postcode";

    private final static Set<String> REQUIRED_TAGS = Set.of(CITY, HOUSENUMBER, STREET, POSTCODE);

    public static ArrayList<Location> getLocations(URI fileUri) {
        ArrayList<Location> locations = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(fileUri);
            if (file.exists() == false) {
                System.err.println("File does not exist");
                return locations;
            }

            OverpassResponse response = mapper.readValue(file, OverpassResponse.class);

            for (Element e : response.elements) {
                if (((e.lat != null && e.lon != null) || (e.center != null && e.center.lat != null && e.center.lon != null)) && e.tags != null && e.tags.keySet().containsAll(REQUIRED_TAGS) && allTagsNotEmptyOrNull(e)) {
                    double lat;
                    double lng;

                    if (e.center != null && e.center.lat != null && e.center.lon != null) {
                        lat = e.center.lat;
                        lng = e.center.lon;
                    } else {
                        lat = e.lat;
                        lng = e.lon;
                    }

                    Location location = new Location(e.tags.get(STREET), e.tags.get(HOUSENUMBER), e.tags.get(POSTCODE), e.tags.get(CITY), lat, lng);
                    if (isAlreadyContained(locations, location)) {
                        System.out.println("Location already exists: " + location);
                        continue;
                    }

                    locations.add(location);
                }
            }
        } catch (IOException ex) {
            System.getLogger(AddressDataLoaderUtil.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return locations;
    }

    private static boolean allTagsNotEmptyOrNull(Element e) {
        for (Map.Entry<String, String> entry : e.tags.entrySet()) {
            if (REQUIRED_TAGS.contains(entry.getKey())) {
                if (StringUtil.isNullOrEmpty(entry.getValue())) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isAlreadyContained(ArrayList<Location> locations, Location location) {
        for (Location existingLocation : locations) {
            if (existingLocation.getLat() == location.getLat() && existingLocation.getLng() == location.getLng()) {
                return true;
            }

            if (existingLocation.getText().equals(location.getText())) {
                return true;
            }

        }

        return false;
    }
}
