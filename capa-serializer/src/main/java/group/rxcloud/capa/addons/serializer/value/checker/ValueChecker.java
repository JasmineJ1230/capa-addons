package group.rxcloud.capa.addons.serializer.value.checker;

public interface ValueChecker<T> {

    void check(T value, String valueName);

}
