package group.rxcloud.capa.addons.serializer.baiji.value;

import java.util.Collection;

public final class CollectionValues {

    public static boolean isNullOrEmpty(Collection<?> value) {
        return value == null || value.isEmpty();
    }

    private CollectionValues() {

    }

}
