package at.faistdev.fwlstsim.dataaccess.loader.addresses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Center {

    public Double lat;
    public Double lon;
}
