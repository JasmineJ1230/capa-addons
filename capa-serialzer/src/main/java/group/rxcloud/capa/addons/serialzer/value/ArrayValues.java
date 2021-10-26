package group.rxcloud.capa.addons.serialzer.value;

import java.util.Arrays;
import java.util.List;

public final class ArrayValues {

    public static final ArrayValues DEFAULT = new ArrayValues();

    private ArrayValues() {

    }

    public static <T> boolean isNullOrEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static <T> void checkNullOrEmpty(T[] value, String valueName) {
        if (isNullOrEmpty(value))
            throw new IllegalArgumentException("argument " + valueName + " is null or empty");
    }

    public static <T> List<T> asList(T[] array) {
        if (array == null)
            return null;

        return Arrays.asList(array);
    }

}
