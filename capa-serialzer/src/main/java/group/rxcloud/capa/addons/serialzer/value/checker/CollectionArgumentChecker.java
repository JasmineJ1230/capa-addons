package group.rxcloud.capa.addons.serialzer.value.checker;

import group.rxcloud.capa.addons.serialzer.value.CollectionValues;

import java.util.Collection;

public class CollectionArgumentChecker implements ValueChecker<Collection<?>> {

    public static final CollectionArgumentChecker DEFAULT = new CollectionArgumentChecker();

    @Override
    public void check(Collection<?> value, String valueName) {
        if (CollectionValues.isNullOrEmpty(value))
            throw new IllegalArgumentException("argument " + valueName + " is null or empty");
    }

}
