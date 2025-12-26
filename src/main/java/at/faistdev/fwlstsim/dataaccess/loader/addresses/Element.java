package at.faistdev.fwlstsim.dataaccess.loader.addresses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Element {

    public String type;
    public long id;
    public Double lat;   // Use Double because some ways/relations might not have lat/lon
    public Double lon;
    public Center center;
    public Map<String, String> tags;  // Only the tags you need will be present here
}
