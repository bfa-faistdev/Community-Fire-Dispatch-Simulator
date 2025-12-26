package at.faistdev.fwlstsim.dataaccess.loader.addresses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OverpassResponse {

    public List<Element> elements;
}
