package group.rxcloud.capa.addons.serialzer.value.checker;

import group.rxcloud.capa.addons.serialzer.value.MapValues;

import java.util.Map;

public class MapArgumentChecker implements ValueChecker<Map<?, ?>> {

    public static final MapArgumentChecker DEFAULT = new MapArgumentChecker();

    @Override
    public void check(Map<?, ?> value, String valueName) {
        if (MapValues.isNullOrEmpty(value))
            throw new IllegalArgumentException("argument " + valueName + " is null or empty");
    }

}
