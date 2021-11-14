package group.rxcloud.capa.addons.serializer.baiji.value.checker;

public interface ValueChecker<T> {

    void check(T value, String valueName);

}
