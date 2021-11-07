package group.rxcloud.capa.addons.serializer.value.checker;

import group.rxcloud.capa.addons.serializer.value.ArrayValues;

import java.util.Collection;
import java.util.Map;

public class ValueCheckers {

    public static void notNull(Object value, String valueName) {
        NullArgumentChecker.DEFAULT.check(value, valueName);
    }

    public static void notNullOrWhiteSpace(String value, String valueName) {
        StringArgumentChecker.DEFAULT.check(value, valueName);
    }

    public static void notNullOrEmpty(Map<?, ?> value, String valueName) {
        MapArgumentChecker.DEFAULT.check(value, valueName);
    }

    public static void notNullOrEmpty(Collection<?> value, String valueName) {
        CollectionArgumentChecker.DEFAULT.check(value, valueName);
    }

    public static <T> void notNullOrEmpty(T[] value, String valueName) {
        if (ArrayValues.isNullOrEmpty(value))
            throw new IllegalArgumentException("argument " + valueName + " is null or empty");
    }

}
