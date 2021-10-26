package group.rxcloud.capa.addons.serialzer.value;

import group.rxcloud.capa.addons.serialzer.delegate.Func;
import group.rxcloud.capa.addons.serialzer.value.checker.NullArgumentChecker;

import java.util.concurrent.ConcurrentHashMap;

public final class ConcurrentHashMapValues {

    private ConcurrentHashMapValues() {

    }

    private static <K, V> void checkArgument(ConcurrentHashMap<K, V> map, K key, Func<V> valueCreator) {
        NullArgumentChecker.DEFAULT.check(map, "map");
        NullArgumentChecker.DEFAULT.check(key, "key");
        NullArgumentChecker.DEFAULT.check(valueCreator, "valueCreator");
    }

    private static <K, V> V internalGetOrAdd(ConcurrentHashMap<K, V> map, K key, Func<V> valueCreator) {
        V value = map.get(key);
        if (value != null)
            return value;

        value = valueCreator.execute();
        if (value == null)
            throw new IllegalArgumentException("valueCreator create a null value!");

        V prev = map.putIfAbsent(key, value);
        return prev == null ? value : prev;
    }

    public static <K, V> V getOrAdd(ConcurrentHashMap<K, V> map, K key, Func<V> valueCreator) {
        checkArgument(map, key, valueCreator);
        return internalGetOrAdd(map, key, valueCreator);
    }

    public static <K, V> V getOrAddWithLock(ConcurrentHashMap<K, V> map, K key, Func<V> valueCreator) {
        checkArgument(map, key, valueCreator);

        V value = map.get(key);
        if (value != null)
            return value;

        return map.computeIfAbsent(key, K -> valueCreator.execute());
    }
}

