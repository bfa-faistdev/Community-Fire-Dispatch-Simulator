package at.faistdev.fwlstsim.ui;

import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;

public class UiRegistry {

    private static Set<JFrame> REGISTERED_UIS = new HashSet<>();

    public static <T extends JFrame> T get(Class<T> clazz) {
        for (JFrame jFrame : REGISTERED_UIS) {
            if (clazz.isInstance(jFrame)) {
                return clazz.cast(jFrame);
            }
        }
        return null;
    }

    public static void add(JFrame ui) {
        REGISTERED_UIS.add(ui);
    }
}
