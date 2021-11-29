package HelperClass;

import java.util.Arrays;

public class EnumConverter {
    public static String[] enumToString(Enum<?>[] e) {
        return Arrays.stream(e).map(Enum::name).toArray(String[]::new);
    }
}
