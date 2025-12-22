package at.faistdev.fwlstsim.bl.util;

public class StringUtil {

    public static boolean isNullOrEmpty(Object value) {
        if (value == null) {
            return true;
        }

        if ((value instanceof String) == false) {
            return true;
        }

        return ((String) value).isBlank();
    }
}
