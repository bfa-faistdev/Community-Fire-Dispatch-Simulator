package at.faistdev.fwlstsim.bl.util;

import java.util.Random;

public class RandomUtil {

    public static int getInt(int start, int end) {
        Random random = new Random();
        return random.nextInt(end - start) + start;
    }
}
