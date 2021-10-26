package group.rxcloud.capa.addons.serialzer.value.checker;

public interface ValueChecker<T> {

    void check(T value, String valueName);

}
